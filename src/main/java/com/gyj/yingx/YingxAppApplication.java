package com.gyj.yingx;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.gyj.yingx.dao")
@org.mybatis.spring.annotation.MapperScan(basePackages = "com.gyj.yingx.dao")
@SpringBootApplication
public class YingxAppApplication {

    public static void main(String[] args) {

        SpringApplication.run(YingxAppApplication.class, args);
    }

}
