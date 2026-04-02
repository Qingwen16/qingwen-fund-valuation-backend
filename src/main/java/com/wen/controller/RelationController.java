package com.wen.controller;

import cn.hutool.core.util.StrUtil;
import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.dto.FundWatchlistDto;
import com.wen.model.dto.FundHoldingDto;
import com.wen.model.vo.HoldingResponse;
import com.wen.service.RelationService;
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
public class RelationController {

    private final RelationService relationService;

    /**
     * 获取用户所有自选基金列表
     *
     * @param userId 用户ID
     * @return 列表
     */
    @GetMapping("/queryWatchlistList")
    public Response<List<FundWatchlistDto>> queryWatchlistList(@Param("userId") Long userId) {
        if (userId == null) {
            throw new BusinessException("输入参数存在空值");
        }
        List<FundWatchlistDto> responses = relationService.queryWatchlistList(userId);
        return Response.success(responses);
    }

    /**
     * 获取用户所有持有基金列表（多账号）
     *
     * @param userId    用户ID
     * @return 用户[账户[列表]]
     */
    @GetMapping("/queryHoldingList")
    public Response<List<HoldingResponse>> queryHoldingList(@Param("userId") Long userId) {
        if (userId == null) {
            throw new BusinessException("输入参数存在空值");
        }
        List<HoldingResponse> responses = relationService.queryHoldingList(userId);
        return Response.success(responses);
    }

    /**
     * 新增自选基金数据
     *
     * @param request 自选基金类
     * @return 无
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
     * 新增自选基金数据
     *
     * @param request 持有基金类
     * @return 无
     */
    @PostMapping("/insertHoldingFund")
    public Response<?> insertHoldingFund(@RequestBody FundHoldingDto request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        relationService.insertHoldingFund(request);
        return Response.success();
    }

    /**
     * 取消自选基金数据
     *
     * @param userId 用户ID
     * @param code   基金代码
     */
    @GetMapping("/deleteWatchlistFund")
    public Response<?> deleteWatchlistFund(@Param("userId") Long userId, @Param("code") String code) {
        if (userId == null || StrUtil.isEmpty(code)) {
            throw new BusinessException("输入参数存在空值");
        }
        relationService.deleteWatchlistFund(userId, code);
        return Response.success();
    }

    /**
     * 取消持有基金数据
     *
     * @param userId    用户ID
     * @param accountId 用户账户ID
     * @param code      基金代码
     */
    @GetMapping("/deleteHoldingFund")
    public Response<?> deleteHoldingFund(@Param("userId") Long userId, @Param("accountId") Long accountId,
                                         @Param("code") String code) {
        if (userId == null || accountId == null || StrUtil.isEmpty(code)) {
            throw new BusinessException("输入参数存在空值");
        }
        relationService.deleteHoldingFund(userId, accountId, code);
        return Response.success();
    }

}
