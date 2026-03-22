package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.common.exception.BusinessException;
import com.wen.mapper.UserAccountMapper;
import com.wen.mapper.UserInfoMapper;
import com.wen.model.entity.UserAccount;
import com.wen.model.entity.UserInfo;
import com.wen.model.vo.DecreasePositionRequest;
import com.wen.model.vo.UserIdRequest;
import com.wen.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    private final UserAccountMapper userAccountMapper;

    @Override
    public UserInfo queryUserInfo(UserIdRequest request) {
        if (request.getUserId() == null) {
            throw new BusinessException("输入参数的用户ID为空");
        }
        UserInfo userInfo = userInfoMapper.selectById(request.getUserId());
        log.info("QueryUserInfo：根据用户ID获取用户信息成功: {}", userInfo.getId());
        return userInfo;
    }

    @Override
    public void deleteUserInfo(UserIdRequest request) {
        if (request.getUserId() == null) {
            throw new BusinessException("输入参数的用户ID为空");
        }
        userInfoMapper.deleteById(request.getUserId());
        log.info("QueryUserInfo：根据用户ID获取用户信息成功: {}", request.getUserId());
    }

    @Override
    public List<UserAccount> queryUserAccount(UserIdRequest request) {
        if (request.getUserId() == null) {
            throw new BusinessException("输入参数的用户ID为空");
        }
        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAccount::getUserId, request.getUserId());
        List<UserAccount> userAccounts = userAccountMapper.selectList(wrapper);
        log.info("QueryUserAccount：根据用户ID获取用户账户信息成功: {}", userAccounts.size());
        return userAccounts;
    }

    @Override
    public void deleteUserAccount(DecreasePositionRequest request) {
        if (request.getUserId() == null || request.getAccountId() == null) {
            throw new BusinessException("输入参数的用户ID为空");
        }
        userAccountMapper.deleteById(request.getAccountId());
        log.info("DeleteUserAccount：根据用户ID获取用户账户信息成功: {}", request.getAccountId());
    }
}
