package com.jsonyao.redis;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Redis测试应用
 */
@SpringBootApplication
public class TestRedisApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TestRedisApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
