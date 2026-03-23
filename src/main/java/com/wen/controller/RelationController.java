package com.wen.controller;

import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.dto.FundWatchlistDto;
import com.wen.model.dto.FundHoldingDto;
import com.wen.model.vo.*;
import com.wen.service.RelationService;
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
public class RelationController {

    private final RelationService relationService;

    /**
     * 获取用户所有自选基金列表
     */
    @PostMapping("/getUserWatchlistFunds")
    public Response<List<FundWatchlistDto>> getUserWatchlistFunds(@RequestBody UserIdRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        List<FundWatchlistDto> responses = relationService.getUserWatchlistFunds(request);
        return Response.success(responses);
    }

    /**
     * 获取用户所有持有基金列表
     */
    @PostMapping("/getUserHoldingFunds")
    public Response<List<FundHoldingDto>> getUserHoldingFunds(@RequestBody AccountRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        List<FundHoldingDto> responses = relationService.getUserHoldingFunds(request);
        return Response.success(responses);
    }

    /**
     * 新增自选基金数据
     */
    @PostMapping("/addWatchlistFund")
    public Response<?> addWatchlistFund(@RequestBody FundWatchlistDto request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        relationService.addWatchlistFund(request);
        return Response.success();
    }

    /**
     * 新增持有基金数据
     */
    @PostMapping("/addHoldingFund")
    public Response<?> addHoldingFund(@RequestBody FundHoldingDto request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        relationService.addHoldingFund(request);
        return Response.success();
    }

}
