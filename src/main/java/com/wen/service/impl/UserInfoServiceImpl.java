package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.common.exception.BusinessException;
import com.wen.mapper.UserInfoMapper;
import com.wen.model.entity.UserInfo;
import com.wen.model.vo.UserIdRequest;
import com.wen.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    @Override
    public UserInfo queryUserInfo(UserIdRequest request) {
        Long userId = request.getUserId();
        if (request.getUserId() == null) {
            throw new BusinessException("输入参数的用户ID为空");
        }
        UserInfo userInfo = userInfoMapper.selectById(userId);
        log.info("QueryUserInfo：根据用户ID获取用户信息成功: {}", userInfo.getId());
        return userInfo;
    }

    @Override
    public void deleteUserInfo(UserIdRequest request) {
        Long userId = request.getUserId();
        if (request.getUserId() == null) {
            throw new BusinessException("输入参数的用户ID为空");
        }
        userInfoMapper.deleteById(userId);
        log.info("QueryUserInfo：根据用户ID获取用户信息成功: {}", userId);
    }
}
