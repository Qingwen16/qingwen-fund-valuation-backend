package com.wen.service;

import com.wen.model.entity.UserAccount;
import com.wen.model.entity.UserInfo;
import com.wen.model.vo.DecreasePositionRequest;
import com.wen.model.vo.UserIdRequest;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public interface UserInfoService {

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
    List<UserAccount> queryUserAccount(UserIdRequest request);

    /**
     * 删除用户账户信息
     */
    void deleteUserAccount(DecreasePositionRequest request);
}
