package com.gyj.yingx.testCache;

import com.gyj.yingx.util.ApplicationContextUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCache {

    @Test
    public void test1(){
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        /*
         * 把redisTemplate 的key序列化改为 StringRedisSerializer
         * */
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        ValueOperations ops = redisTemplate.opsForValue();
        Object gyj = ops.get("gyj");
        System.out.println("gyj = " + gyj);

    }
}
