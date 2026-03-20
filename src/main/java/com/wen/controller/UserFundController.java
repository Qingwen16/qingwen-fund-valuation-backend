package com.wen.controller;

import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.vo.UserFundRequest;
import com.wen.model.vo.UserFundResponse;
import com.wen.model.vo.UserIdRequest;
import com.wen.service.UserFundService;
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
public class UserFundController {

    private final UserFundService userFundService;

    /**
     * 获取用户所有自选基金列表
     */
    @PostMapping("/getUserSelectionFunds")
    public Response<List<UserFundResponse>> getUserSelectionFunds(@RequestBody UserIdRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        List<UserFundResponse> responses = userFundService.getUserSelectionFunds(request);
        return Response.success(responses);
    }

    /**
     * 获取用户所有持有基金列表
     */
    @PostMapping("/getUserHoldingFunds")
    public Response<List<UserFundResponse>> getUserHoldingFunds(@RequestBody UserIdRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        List<UserFundResponse> responses = userFundService.getUserHoldingFunds(request);
        return Response.success(responses);
    }


    /**
     * 新增自选基金数据
     */
    @PostMapping("/addSelectionFund")
    public Response<?> addSelectionFund(@RequestBody UserFundRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        userFundService.addSelectionFund(request);
        return Response.success();
    }

    /**
     * 新增持有基金数据
     */
    @PostMapping("/addHoldingFund")
    public Response<?> addHoldingFund(@RequestBody UserFundRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        userFundService.addHoldingFund(request);
        return Response.success();
    }

    /**
     * 更新持有基金数据
     */
    @PostMapping("/uptHoldingFund")
    public Response<?> uptHoldingFund(@RequestBody UserFundRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        userFundService.addHoldingFund(request);
        return Response.success();
    }
}
