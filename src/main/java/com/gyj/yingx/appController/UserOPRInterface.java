package com.gyj.yingx.appController;

import com.gyj.yingx.common.CommonResult;
import com.gyj.yingx.entity.User;
import com.gyj.yingx.service.UserService;
import com.gyj.yingx.util.AliyunSendShortMessage;
import com.gyj.yingx.util.ApplicationContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("app")
public class UserOPRInterface {

    @Autowired
    private UserService userService;

    @RequestMapping("getPhoneCode")
    public CommonResult getPhoneCode(String phone) {
        //获取随机数
        try {
            String random = AliyunSendShortMessage.getRandom(6);

            RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
            /*
             * 把redisTemplate 的key序列化改为 StringRedisSerializer
             * */
            StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(stringRedisSerializer);

            ValueOperations ops = redisTemplate.opsForValue();
            ops.set(phone, random);
            redisTemplate.expire(phone, 5, TimeUnit.MINUTES);

            //发送验证码
            String message = AliyunSendShortMessage.sendCode(phone, random);
            return new CommonResult().success(message, phone);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed("验证码发送失败");
        }

    }

    @RequestMapping("login")
    public CommonResult login(String phone, String phoneCode) {

        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        /*
         * 把redisTemplate 的key序列化改为 StringRedisSerializer
         * */
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);

        ValueOperations ops = redisTemplate.opsForValue();
        Object code = ops.get(phone);
        if (phoneCode.equals(code)) {
            CommonResult commonResult = null;
            User user = userService.queryUserByPhoneNUmber(phone);
            if(user!=null){
                commonResult = commonResult.success("100","登录成功",user);
            }else{
                user = new User();
                user.setPhonenumber(phone);
                user.setCreateDate(new Date());
                user.setStatus("1");
                String uuid = userService.insertUser(user);
                user.setId(uuid);
                commonResult = commonResult.failed("101","错误信息",null);
            }

            return commonResult;
        }
        return new CommonResult().failed("101","错误信息",null);
    }

}
