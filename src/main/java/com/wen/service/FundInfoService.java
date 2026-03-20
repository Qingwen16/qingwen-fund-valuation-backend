package com.wen.service;

import com.wen.model.dto.FundInfoDto;
import com.wen.model.vo.UserFundRequest;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public interface FundInfoService {

    /**
     * 新增基金信息
     */
    void insertFundInfo(FundInfoDto request);

    /**
     * 新增基金信息
     */
    void insertFundInfo(UserFundRequest request);

}
