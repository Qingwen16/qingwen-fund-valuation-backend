package com.wen.service;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public interface CacheService {

    /**
     * 设置用户Token到缓存
     */
    void setUserToken(String openId, String token);

    /**
     * 获取用户Token到缓存
     */
    String getUserToken(String openId);

    /**
     * 删除用户Token到缓存
     */
    void detUserToken(String openId);

    /**
     * 设置用户黑名单Key
     */
    void setTokenBlacklist(String token);

    /**
     * 检验Token是否存在黑名单
     */
    Boolean hasTokenBlacklist(String token);
}
