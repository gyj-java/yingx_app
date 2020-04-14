package com.gyj.yingx.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/*
*
* 在容器中 获得 组件
* */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext =applicationContext;
    }

    /*
    *  根据组件的名字
    * */
    public static Object getBean(String id){
        Object bean = applicationContext.getBean(id);
        return bean;
    }
    /*
     *  根据组件的类型
     * */
    public static Object getBean(Class clazz){
        Object bean = applicationContext.getBean(clazz);
        return bean;
    }
    /*
     *  根据组件的名字,类型
     * */
    public static Object getBean(String id,Class clazz){
        Object bean = applicationContext.getBean(id,clazz);
        return bean;
    }
}
