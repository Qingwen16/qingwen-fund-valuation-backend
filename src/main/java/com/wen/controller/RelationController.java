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
    @PostMapping("/queryUserWatchlistFunds")
    public Response<List<FundWatchlistDto>> queryUserWatchlistFunds(@RequestBody UserIdRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        List<FundWatchlistDto> responses = relationService.queryUserWatchlistFunds(request);
        return Response.success(responses);
    }

    /**
     * 获取用户所有持有基金列表
     */
    @PostMapping("/queryUserHoldingFunds")
    public Response<List<FundHoldingDto>> queryUserHoldingFunds(@RequestBody AccountRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        List<FundHoldingDto> responses = relationService.queryUserHoldingFunds(request);
        return Response.success(responses);
    }

    /**
     * 新增自选基金数据
     */
    @PostMapping("/insertWatchlistFund")
    public Response<?> insertWatchlistFund(@RequestBody FundWatchlistDto request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        relationService.insertWatchlistFund(request);
        return Response.success();
    }

    /**
     * 新增持有基金数据
     */
    @PostMapping("/insertHoldingFund")
    public Response<?> insertHoldingFund(@RequestBody FundHoldingDto request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        relationService.insertHoldingFund(request);
        return Response.success();
    }

}
