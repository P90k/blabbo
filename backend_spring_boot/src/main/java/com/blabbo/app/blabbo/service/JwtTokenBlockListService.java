package com.blabbo.app.blabbo.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenBlockListService {
    private final StringRedisTemplate redisTemplate;


    public JwtTokenBlockListService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void blockToken(String jti, long ExpirationInSeconds) {
        redisTemplate.opsForValue()
                     .set(jti, "blocked", ExpirationInSeconds,
                          TimeUnit.SECONDS);
    }


    public Boolean isTokenBlocked(String jti) {
        return redisTemplate.hasKey(jti);
    }
}
