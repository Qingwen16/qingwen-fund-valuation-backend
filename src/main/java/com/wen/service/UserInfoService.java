package com.wen.service;

import com.wen.model.entity.UserInfo;
import com.wen.model.vo.UserIdRequest;

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
}
