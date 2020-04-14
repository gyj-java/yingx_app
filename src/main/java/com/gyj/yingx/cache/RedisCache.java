package com.gyj.yingx.cache;


import com.gyj.yingx.util.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.locks.ReadWriteLock;


public class RedisCache implements Cache {
    private String id;// 例如: com.baizhi.dao.AdminDao

    public RedisCache(String id) {
        this.id=id;
    }

    @Override
    public String getId() {
        return id;
    }

    //添加缓存
    @Override
    public void putObject(Object key, Object value) {
        System.out.println("***********加入缓存************");
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        /*
        * 把redisTemplate 的key序列化改为 StringRedisSerializer
        * */
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);

        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(id,key.toString(),value);

    }

    /*
        查询缓存:
            有缓存   : 返回缓存中的数据
            没有缓存 : 调用 添加缓存的方法putObject 添加缓存, 返回数据
            return null: 没有缓存
     */
    @Override
    public Object getObject(Object key) {
        System.out.println("***********查看缓存**********");
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        /*
         * 把redisTemplate 的key序列化改为 StringRedisSerializer
         * */
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        Boolean aBoolean = redisTemplate.opsForHash().hasKey(id, key.toString());
        if(aBoolean){
            Object o = redisTemplate.opsForHash().get(id, key.toString());
            return o;
        }
        return null;
    }

    @Override
    public void clear() {
        System.out.println("*************清空缓存***********");
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        /*
         * 把redisTemplate 的key序列化改为 StringRedisSerializer
         * */
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.delete(id);
    }


    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
