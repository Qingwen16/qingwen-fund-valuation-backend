package com.wen.controller;

import com.wen.model.entity.FundValUserInfo;
import com.wen.model.vo.*;
import com.wen.service.FundValuationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : rjw
 * @date : 2026-03-12
 */
@RestController
@RequestMapping("/fund/valuation")
@RequiredArgsConstructor
public class FundValuationController {

    private FundValuationService fundValuationService;

    /**
     * 根据用户ID获取用户数据
     */
    @PostMapping("/queryUserInfo")
    public Response<?> queryUserInfo(@RequestBody FundValUserIdRequest request) {
        FundValUserInfo fundValUserInfo = fundValuationService.queryUserInfo(request);
        return Response.success();
    }

    /**
     * 根据用户ID修改用户数据
     */
    @PostMapping("/updateUserInfo")
    public Response<?> updateUserInfo(@RequestBody FundValUserInfoRequest request) {
        fundValuationService.updateUserInfo(request);
        return Response.success();
    }

    /**
     * 根据用户ID删除用户数据
     */
    @PostMapping("/deleteUserInfo")
    public Response<?> deleteUserInfo(@RequestBody FundValUserIdRequest request) {
        fundValuationService.deleteUserInfo(request);
        return Response.success();
    }

    /**
     * 新增持有基金数据
     */
    @PostMapping("/insertHoldingFundInfo")
    public Response<?> insertHoldingFundInfo(@RequestBody FundValUserFundRequest request) {
        fundValuationService.insertHoldingFundInfo(request);
        return Response.success();
    }

    /**
     * 新增自选基金数据
     */
    @PostMapping("/insertSelectionFundInfo")
    public Response<?> insertSelectionFundInfo(@RequestBody FundValUserFundRequest request) {
        fundValuationService.insertSelectionFundInfo(request);
        return Response.success();
    }

    /**
     * 删除基金数据
     */
    @PostMapping("/deleteFundInfo")
    public Response<?> deleteFundInfo(@RequestBody FundValUserFundRequest request) {
        fundValuationService.deleteFundInfo(request);
        return Response.success();
    }

    /**
     * 获取用户持有的基金
     */
    @PostMapping("/queryUserFundHolding")
    public Response<?> queryUserFundHolding(@RequestBody FundValUserIdRequest request) {
        fundValuationService.queryUserFundHolding(request);
        return Response.success();
    }

    /**
     * 获取用户自选的基金
     */
    @PostMapping("/queryUserFundSelection")
    public Response<?> queryUserFundSelection(@RequestBody FundValUserIdRequest request) {
        fundValuationService.queryUserFundSelection(request);
        return Response.success();
    }

}
