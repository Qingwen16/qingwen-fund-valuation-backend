package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.common.exception.BusinessException;
import com.wen.mapper.AccountInfoMapper;
import com.wen.mapper.UserInfoMapper;
import com.wen.model.entity.AccountInfo;
import com.wen.model.entity.UserInfo;
import com.wen.model.vo.AccountRequest;
import com.wen.model.vo.UserIdRequest;
import com.wen.service.UserService;
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
public class UserServiceImpl implements UserService {

    private final UserInfoMapper userInfoMapper;

    private final AccountInfoMapper userAccountMapper;

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
    public List<AccountInfo> queryUserAccount(UserIdRequest request) {
        if (request.getUserId() == null) {
            throw new BusinessException("输入参数的用户ID为空");
        }
        LambdaQueryWrapper<AccountInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountInfo::getUserId, request.getUserId());
        List<AccountInfo> accountInfoList = userAccountMapper.selectList(wrapper);
        log.info("QueryUserAccount：根据用户ID获取用户账户信息成功: {}", accountInfoList.size());
        return accountInfoList;
    }

    @Override
    public void deleteUserAccount(AccountRequest request) {
        if (request.getUserId() == null || request.getAccountId() == null) {
            throw new BusinessException("输入参数的用户ID为空");
        }
        userAccountMapper.deleteById(request.getAccountId());
        log.info("DeleteUserAccount：根据用户ID获取用户账户信息成功: {}", request.getAccountId());
    }
}

