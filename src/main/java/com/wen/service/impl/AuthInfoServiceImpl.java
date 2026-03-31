package com.wen.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.common.constant.AuthConstants;
import com.wen.config.WxConfig;
import com.wen.model.entity.UserInfo;
import com.wen.model.vo.WxLoginResponse;
import com.wen.model.vo.WxSession;
import com.wen.service.AuthInfoService;
import com.wen.service.CacheService;
import com.wen.service.UserInfoService;
import com.wen.utils.JwtUtil;
import com.wen.utils.UserInfoContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthInfoServiceImpl implements AuthInfoService {

    private final WxConfig wxConfig;

    private final JwtUtil jwtUtil;

    private final CacheService cacheService;

    private final UserInfoService userInfoService;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 微信用户登录
     */
    @Override
    public WxLoginResponse login(String code) {
        // 0. 调用微信接口换取 openid
        WxSession wxSession = getWxSession(code);

        if (wxSession == null || wxSession.getOpenid() == null || wxSession.getUnionid() == null) {
            log.error("微信登录失败，无法获取 openid, 无法获取unionid: {}", wxSession);
            throw new RuntimeException("微信登录失败，无法获取 openid, 无法获取unionid");
        }

        // 1. 登录成功，查询用户信息
        UserInfo userInfo = userInfoService.queryUserInfoByOpenId(wxSession.getOpenid());

        // 2. 不存在则自动注册用户信息和账户
        if (userInfo == null) {
            userInfo = userInfoService.registerUserByWxSession(wxSession);
            userInfoService.createUserAccount(userInfo.getUserId(), "默认账户");
        }

        // 3. 生成token
        String token = jwtUtil.generateToken(userInfo.getUserId(), userInfo.getOpenid());
        cacheService.setUserToken(userInfo.getOpenid(), token);

        UserInfoContext.setUserInfo(userInfo);

        WxLoginResponse response = new WxLoginResponse();
        response.setToken(token);
        response.setOpenid(userInfo.getOpenid());
        response.setUserId(userInfo.getUserId());

        return response;
    }

    /**
     * 调用微信 code2Session 接口
     */
    private WxSession getWxSession(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", wxConfig.getAppId());
        params.put("secret", wxConfig.getAppSecret());
        params.put("code", code);

        try {
            String result = restTemplate.getForObject(AuthConstants.WX_CODE2SESSION_URL, String.class, params);
            return objectMapper.readValue(result, WxSession.class);
        } catch (Exception e) {
            throw new RuntimeException("调用微信 code2Session 接口失败: " + e.getMessage());
        }
    }

    /**
     * 退出登录主流程：
     * 1. 校验 token 合法性
     * 2. 将 token 加入 Redis 黑名单（TTL 与 token 剩余有效期一致）
     */
    @Override
    public void logout() {

        UserInfo userInfo = UserInfoContext.getUserInfo();
        // 获取用户缓存
        String userToken = cacheService.getUserToken(userInfo.getOpenid());

        // 下线token并加入黑名单
        if (userToken != null) {
            cacheService.detUserToken(userInfo.getOpenid());
            cacheService.setTokenBlacklist(userToken);
        }
        UserInfoContext.clear();
    }
}


