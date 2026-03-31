package com.wen.service;

import com.wen.model.dto.FundHoldingDto;
import com.wen.model.dto.FundWatchlistDto;
import com.wen.model.vo.AccountRequest;
import com.wen.model.vo.UserIdRequest;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-31
 */
public interface RelationService {

    /**
     * 获取用户所有自选基金列表
     */
    List<FundWatchlistDto> queryUserWatchlistFunds(UserIdRequest request);

    /**
     * 获取用户所有持有基金列表
     */
    List<FundHoldingDto> queryUserHoldingFunds(AccountRequest request);

    /**
     * 添加自选基金
     */
    boolean existsWatchlistFund(FundWatchlistDto request);

    /**
     * 新增持有基金数据
     */
    boolean existsHoldingFund(FundHoldingDto request);

    /**
     * 添加自选基金
     */
    void insertWatchlistFund(FundWatchlistDto request);

    /**
     * 新增持有基金数据
     */
    void insertHoldingFund(FundHoldingDto request);

    /**
     * 删除自选基金
     */
    void deleteWatchlistFund(FundWatchlistDto request);

    /**
     * 删除持有基金数据
     */
    void deleteHoldingFund(FundHoldingDto request);

}
