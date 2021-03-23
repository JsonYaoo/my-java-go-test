package com.jsonyao.redis.tests;

import com.jsonyao.redis.TestRedisApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Redis测试应用
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestRedisApplication.class})
public class RedisApplicationTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testBlpop(){
        System.err.println("开始了...");
        String value = redisTemplate.opsForList().leftPop("test", 3, TimeUnit.SECONDS);
        System.err.println(value);
    }

}
