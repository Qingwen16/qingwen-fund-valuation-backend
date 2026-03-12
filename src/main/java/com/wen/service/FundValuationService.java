package com.wen.service;

import com.wen.model.entity.FundValUserInfo;
import com.wen.model.vo.FundValUserFundRequest;
import com.wen.model.vo.FundValUserIdRequest;
import com.wen.model.vo.FundValUserInfoRequest;

/**
 * @author : rjw
 * @date : 2026-03-12
 */
public interface FundValuationService {

    /**
     * 根据用户ID获取用户信息
     */
    FundValUserInfo queryUserInfo(FundValUserIdRequest request);

    /**
     * 根据用户ID修改用户信息
     */
    void updateUserInfo(FundValUserInfoRequest request);

    /**
     * 根据用户ID删除用户信息
     */
    void deleteUserInfo(FundValUserIdRequest request);

    /**
     * 新增持有基金数据
     */
    void insertHoldingFundInfo(FundValUserFundRequest request);

    /**
     * 新增自选基金数据
     */
    void insertSelectionFundInfo(FundValUserFundRequest request);

    /**
     * 删除基金数据
     */
    void deleteFundInfo(FundValUserFundRequest request);

    /**
     * 查询用户持有的基金
     */
    void queryUserFundHolding(FundValUserIdRequest request);

    /**
     * 查询用户自选的基金
     */
    void queryUserFundSelection(FundValUserIdRequest request);
}
