package com.wen.service;

import com.wen.model.entity.FundInfo;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public interface FundService {

    /**
     * 新增基金信息
     */
    void insertFundInfo(FundInfo request);

    /**
     * 新增基金信息
     */
    void deleteFundInfo(String code);

}
