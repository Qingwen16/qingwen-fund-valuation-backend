package com.wen.service;

import com.wen.model.vo.UserFundRequest;
import com.wen.model.vo.UserFundResponse;
import com.wen.model.vo.UserIdRequest;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public interface UserFundService {

    /**
     * 获取用户所有自选基金列表
     */
    List<UserFundResponse> getUserSelectionFunds(UserIdRequest request);

    /**
     * 获取用户所有持有基金列表
     */
    List<UserFundResponse> getUserHoldingFunds(UserIdRequest request);

    /**
     * 添加自选基金
     */
    void addSelectionFund(UserFundRequest request);

    /**
     * 新增持有基金数据
     */
    void addHoldingFund(UserFundRequest request);



}
