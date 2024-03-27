package com.gct;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient  //开启服务注册与发现
@MapperScan("com.gct.mapper")   //指定要变成实现类的接口所在的包
//在不使用@MapperScan前，我们需要直接在Mapper类上面添加注解@Mapper，这种方式要求每一个Mapper类都需要添加此注解，非常麻烦，属于重复劳动。
//通过使用@MapperScan注解，可以让我们不用为每个Mapper类都添加@Mapper注解。
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}