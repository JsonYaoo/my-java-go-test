package com.jsonyao.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

//@PropertySource(value = "classpath:application.properties")
// @PropertySource加载properties: 使用默认工厂DefaultPropertySourceFactory加载属性
// @ImportResource是加载Bean不是加载properties
// @PropertySource加载yml: 需要提供YamlPropertySourceLoader来加载yml属性
@PropertySource(value = "classpath:application-dev.yml", factory = MyYamlPropertySourceFactory.class)
@SpringBootApplication
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class);
    }
}
