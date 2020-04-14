package com.gyj.yingx.advice;

import com.gyj.yingx.annotation.AddLog;
import com.gyj.yingx.dao.LogDao;
import com.gyj.yingx.entity.Admin;
import com.gyj.yingx.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;


@Aspect
@Configuration
public class LogAdvice  {

    @Resource
    HttpSession session;

    @Autowired
    LogDao logDao;

    @Around("@annotation(com.gyj.yingx.annotation.AddLog)")
    public Object addLogs(ProceedingJoinPoint joinPoint){

        //谁   时间   操作   是否成功
        Admin admin = (Admin) session.getAttribute("admin");

        //操作   哪个方法
        String methodName = joinPoint.getSignature().getName();

        //获取方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取方法上的注解
        AddLog addLog = method.getAnnotation(AddLog.class);

        //获取注解中属性的值   value
        String methodDes= addLog.value();

        //放行方法
        try {
            Object proceed = joinPoint.proceed();

            String message="success";

            //System.out.println("管理员："+admin.getUsername()+"--时间："+new Date()+"--操作："+methodName+"--状态："+message);
            //admin.getUsername()
            Log log = new Log(UUID.randomUUID().toString(),"郭云杰",new Date(),methodDes+"("+methodName+")",message);
            logDao.insertSelective(log);
            System.out.println("数据库插入"+log);

            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message="fail";
            //admin.getUsername()
            Log log = new Log(UUID.randomUUID().toString(),"郭云杰",new Date(),methodDes+"("+methodName+")",message);

            logDao.insertSelective(log);

            System.out.println("数据库插入"+log);
            return null;
        }



    }

}
