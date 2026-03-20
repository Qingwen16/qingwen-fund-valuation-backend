package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.common.constant.AuthConstants;
import com.wen.config.WxConfig;
import com.wen.mapper.UserInfoMapper;
import com.wen.model.entity.UserInfo;
import com.wen.model.vo.WxLoginResponse;
import com.wen.model.vo.WxSession;
import com.wen.service.AuthService;
import com.wen.service.CacheService;
import com.wen.utils.JwtUtil;
import com.wen.utils.UserIdGenerator;
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
public class AuthServiceImpl implements AuthService {

    private final WxConfig wxConfig;

    private final JwtUtil jwtUtil;

    private final UserInfoMapper userInfoMapper;

    private final CacheService cacheService;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 微信用户登录
     */
    @Override
    public WxLoginResponse login(String code) {
        // Step 1: 调用微信接口换取 openid
        WxSession wxSession = getWxSession(code);

        if (wxSession == null || wxSession.getOpenid() == null) {
            throw new RuntimeException("微信登录失败，无法获取 openid");
        }

        // Step 2: 查询用户，不存在则自动注册
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getOpenid, wxSession.getOpenid());
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);

        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setUserId(UserIdGenerator.generator());
            userInfo.setOpenid(wxSession.getOpenid());
            userInfo.setUnionid(wxSession.getUnionid());
            userInfo.setCreateTime(System.currentTimeMillis());
            userInfoMapper.insert(userInfo);
        }

        String token = jwtUtil.generateToken(userInfo.getUserId(), userInfo.getOpenid());

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


