package com.wen.service;

import com.wen.model.entity.AccountInfo;
import com.wen.model.entity.UserInfo;
import com.wen.model.vo.AccountRequest;
import com.wen.model.vo.UserIdRequest;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public interface UserService {

    /**
     * 获取用户信息
     */
    UserInfo queryUserInfo(UserIdRequest request);

    /**
     * 删除用户信息
     */
    void deleteUserInfo(UserIdRequest request);

    /**
     * 获取用户账户信息
     */
    List<AccountInfo> queryUserAccount(UserIdRequest request);

    /**
     * 删除用户账户信息
     */
    void deleteUserAccount(AccountRequest request);
}
