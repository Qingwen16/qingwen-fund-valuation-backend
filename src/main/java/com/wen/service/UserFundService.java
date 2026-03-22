package com.wen.service;

import com.wen.model.vo.*;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public interface UserFundService {

    /**
     * 获取用户所有自选基金列表
     */
    List<FundWatchlistResp> getUserWatchlistFunds(UserIdRequest request);

    /**
     * 获取用户所有持有基金列表
     */
    List<FundHoldingResp> getUserHoldingFunds(DecreasePositionRequest request);

    /**
     * 添加自选基金
     */
    void addWatchlistFund(FundWatchlistReq request);

    /**
     * 新增持有基金数据
     */
    void addHoldingFund(FundHoldingReq request);

    /**
     * 新增持有基金数据
     */
    void increaseHoldingFund(UptFundRequest request);

    /**
     * 减少持有基金数据
     */
    void decreaseHoldingFund(UptFundRequest request);

}
