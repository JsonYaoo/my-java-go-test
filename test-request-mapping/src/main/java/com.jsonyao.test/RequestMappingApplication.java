package com.jsonyao.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RequestMapping测试应用: 测试继承关系
 * => 结论:
 *      1) 类级别的RequestMapping: 从左到右谁近的谁生效(也即从类到接口, 抽象类的不算), 谁层级低的谁生效(也即从类到接口, 抽象类的不算), 如果都类(除了抽象类)和接口都没有注解,
 *  *      那最后再看抽象类上有无注解
 *      2) 方法级别的RequestMapping: 谁实现的谁生效, 如果没有实现方法或者实现方法上没有注解, 则回溯到顶层接口的方法注解(抽象方法注解不会生效)
 */
@SpringBootApplication
public class RequestMappingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestMappingApplication.class, args);
    }
}
