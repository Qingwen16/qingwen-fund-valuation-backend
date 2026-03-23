package com.wen.controller;

import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.entity.AccountInfo;
import com.wen.model.entity.UserInfo;
import com.wen.model.vo.AccountRequest;
import com.wen.model.vo.UserIdRequest;
import com.wen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@RestController
@RequestMapping("/fund/valuation")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 根据用户ID获取用户数据
     */
    @PostMapping("/queryUserInfo")
    public Response<UserInfo> queryUserInfo(@RequestBody UserIdRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        UserInfo userInfo = userService.queryUserInfo(request);
        return Response.success();
    }

    /**
     * 根据用户ID获取用户数据
     */
    @PostMapping("/deleteUserInfo")
    public Response<?> deleteUserInfo(@RequestBody UserIdRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        userService.deleteUserInfo(request);
        return Response.success();
    }

    /**
     * 根据用户ID获取用户账户信息
     */
    @PostMapping("/queryUserAccount")
    public Response<List<AccountInfo>> queryUserAccount(@RequestBody UserIdRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        List<AccountInfo> accountInfoList = userService.queryUserAccount(request);
        return Response.success(accountInfoList);
    }

    /**
     * 根据用户ID删除用户账户信息
     */
    @PostMapping("/deleteUserAccount")
    public Response<?> deleteUserAccount(@RequestBody AccountRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        userService.deleteUserAccount(request);
        return Response.success();
    }

}
