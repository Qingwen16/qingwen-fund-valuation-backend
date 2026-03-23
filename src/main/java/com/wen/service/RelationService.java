package com.wen.service;

import com.wen.model.dto.FundWatchlistDto;
import com.wen.model.dto.FundHoldingDto;
import com.wen.model.vo.*;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public interface RelationService {

    /**
     * 获取用户所有自选基金列表
     */
    List<FundWatchlistDto> getUserWatchlistFunds(UserIdRequest request);

    /**
     * 获取用户所有持有基金列表
     */
    List<FundHoldingDto> getUserHoldingFunds(AccountRequest request);

    /**
     * 添加自选基金
     */
    void addWatchlistFund(FundWatchlistDto request);

    /**
     * 新增持有基金数据
     */
    void addHoldingFund(FundHoldingDto request);

}
