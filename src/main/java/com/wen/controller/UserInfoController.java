package com.wen.controller;

import cn.hutool.core.util.StrUtil;
import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.entity.AccountInfo;
import com.wen.model.entity.UserInfo;
import com.wen.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    /**
     * 根据用户ID获取用户数据
     */
    @GetMapping("/queryUserInfo")
    public Response<UserInfo> queryUserInfo(@Param("userId") Long userId) {
        checkLongParam(userId);
        UserInfo userInfo = userInfoService.queryUserByUserId(userId);
        return Response.success(userInfo);
    }

    /**
     * 根据用户ID获取用户数据
     */
    @GetMapping("/deleteUserInfo")
    public Response<?> deleteUserInfo(@Param("userId") Long userId) {
        checkLongParam(userId);
        userInfoService.deleteUserInfo(userId);
        return Response.success();
    }

    /**
     * 根据用户ID获取用户账户信息
     */
    @GetMapping("/queryUserAccount")
    public Response<List<AccountInfo>> queryUserAccount(@Param("userId") Long userId) {
        checkLongParam(userId);
        List<AccountInfo> accountInfoList = userInfoService.queryUserAccount(userId);
        return Response.success(accountInfoList);
    }

    /**
     * 根据用户ID更新用户账户信息
     */
    @GetMapping("/updateUserAccount")
    public Response<?> updateUserAccount(@Param("userId") Long userId,
                                         @Param("accountId") Long accountId,
                                         @Param("name") String name) {
        if (userId == null || accountId == null || StrUtil.isEmpty(name)) {
            throw new BusinessException("输入参数存在空值");
        }
        userInfoService.updateUserAccount(userId, accountId, name);
        return Response.success();
    }

    /**
     * 根据用户ID删除用户账户信息
     */
    @GetMapping("/deleteUserAccount")
    public Response<?> deleteUserAccount(@Param("accountId") Long accountId) {
        checkLongParam(accountId);
        userInfoService.deleteUserAccount(accountId);
        return Response.success();
    }

    private void checkLongParam(Long id) {
        if (id == null) {
            throw new BusinessException("输入参数存在空值");
        }
    }

}
