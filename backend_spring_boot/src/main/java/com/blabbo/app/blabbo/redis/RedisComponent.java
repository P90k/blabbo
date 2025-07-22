package com.blabbo.app.blabbo.redis;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisComponent {

    private final StringRedisTemplate template;


    public RedisComponent(StringRedisTemplate template) {
        this.template = template;
    }


    @PostConstruct
    public void testRedis() {
        template.opsForValue().set("test-key", "Hello world!");
        String val = template.opsForValue().get("test-key");
        System.out.println("Redis test key value: " + val);
    }
}
