package com.wen.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Data
@Configuration
public class CacheConfig {

    @Value("${cache.keyNameSpace}")
    private String keyNameSpace;

    @Value("${cache.defaultKeyVersion}")
    private String defaultKeyVersion;

    private TimeUnit defaultTimeUnit = TimeUnit.SECONDS;

    private long tokenTimeout = 7 * 24 * 60;

    /**
     * 项目 key 前缀
     */
    public String prefix() {
        return keyNameSpace + ":" + defaultKeyVersion + ":";
    }

    /**
     * 用户tokenKey
     */
    public String getKeyUserToken(String openId) {
        return prefix() + "UserToken:" + openId;
    }

    /**
     * 用户token黑名单
     */
    public String getKeyTokenBlacklist(String token) {
        return prefix() + "TokenBlacklist:" + token;
    }

}
