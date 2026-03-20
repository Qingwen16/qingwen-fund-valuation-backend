package com.wen.service.impl;

import com.wen.config.CacheConfig;
import com.wen.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final CacheConfig cacheConfig;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setUserToken(String openId, String token) {
        String userToken = cacheConfig.getKeyUserToken(openId);
        redisTemplate.opsForValue().set(userToken, token, cacheConfig.getTokenTimeout(),
                cacheConfig.getDefaultTimeUnit());
    }

    @Override
    public String getUserToken(String openId) {
        String userToken = cacheConfig.getKeyUserToken(openId);
        return (String) redisTemplate.opsForValue().get(userToken);
    }

    @Override
    public void detUserToken(String openId) {
        String userToken = cacheConfig.getKeyUserToken(openId);
        redisTemplate.delete(userToken);
    }

    @Override
    public void setTokenBlacklist(String token) {
        String tokenKey = cacheConfig.getKeyTokenBlacklist(token);
        redisTemplate.opsForValue().set(tokenKey, "1", cacheConfig.getTokenTimeout(),
                cacheConfig.getDefaultTimeUnit());
    }

    @Override
    public Boolean hasTokenBlacklist(String token) {
        String tokenKey = cacheConfig.getKeyTokenBlacklist(token);
        return Boolean.TRUE.equals(redisTemplate.hasKey(tokenKey));
    }
}
