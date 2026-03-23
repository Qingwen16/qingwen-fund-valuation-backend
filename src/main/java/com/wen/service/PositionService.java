package com.wen.service;

import com.wen.model.vo.SharesRequest;

/**
 * @author : rjw
 * @date : 2026-03-23
 */
public interface PositionService {

    /**
     * 修改持仓
     */
    void changePosition(SharesRequest request);

    /**
     * 增加持仓
     */
    void increasePosition(SharesRequest request);

    /**
     * 减少持仓
     */
    void decreasePosition(SharesRequest request);
}
