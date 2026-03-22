package com.wen.controller;

import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.vo.*;
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
    @PostMapping("/getUserWatchlistFunds")
    public Response<List<FundWatchlistResp>> getUserWatchlistFunds(@RequestBody UserIdRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        List<FundWatchlistResp> responses = userFundService.getUserWatchlistFunds(request);
        return Response.success(responses);
    }

    /**
     * 获取用户所有持有基金列表
     */
    @PostMapping("/getUserHoldingFunds")
    public Response<List<FundHoldingResp>> getUserHoldingFunds(@RequestBody DecreasePositionRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        List<FundHoldingResp> responses = userFundService.getUserHoldingFunds(request);
        return Response.success(responses);
    }

    /**
     * 新增自选基金数据
     */
    @PostMapping("/addWatchlistFund")
    public Response<?> addWatchlistFund(@RequestBody FundWatchlistReq request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        userFundService.addWatchlistFund(request);
        return Response.success();
    }

    /**
     * 新增持有基金数据
     */
    @PostMapping("/addHoldingFund")
    public Response<?> addHoldingFund(@RequestBody FundHoldingReq request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        userFundService.addHoldingFund(request);
        return Response.success();
    }

    /**
     * 持有基金加仓数据
     */
    @PostMapping("/increaseHoldingFund")
    public Response<?> increaseHoldingFund(@RequestBody UptFundRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        userFundService.increaseHoldingFund(request);
        return Response.success();
    }

    /**
     * 持有基金减仓数据
     */
    @PostMapping("/decreaseHoldingFund")
    public Response<?> decreaseHoldingFund(@RequestBody UptFundRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        userFundService.decreaseHoldingFund(request);
        return Response.success();
    }
}
