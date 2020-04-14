package com.gyj.yingx.goeasy;


import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class TestGoEasy {

    @Test
    public void test1() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-20905462639143a98df5fdbd74a82c9e");
        goEasy.publish("my_channel", "Hello, GoEasy!");
    }

    @Test
    public void test2(){
        for (int i = 0; i < 10; i++) {

            Random random = new Random();
            //获取随机数  参数i：50  0<=i<50
            //int i = random.nextInt(50);

            HashMap<String, Object> map = new HashMap<>();

            //根据月份 性别 查询数量   //查询用户信息
            map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月"));
            map.put("boys", Arrays.asList(random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(200)));
            map.put("girls", Arrays.asList(random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(500)));

            //将对象转为json格式字符串
            String content = JSON.toJSONString(map);

            //配置发送消息的必要配置  参数：regionHost,服务器地址,自己的appKey
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-20905462639143a98df5fdbd74a82c9e");

            //配置发送消息  参数:管道名称（自定义）,发送内容
            goEasy.publish("yingxChannel-gyj", content);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String sa;

        }
    }
}
