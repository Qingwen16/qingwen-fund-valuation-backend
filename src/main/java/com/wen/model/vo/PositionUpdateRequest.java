package com.wen.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : 青灯文案
 * @Date: 2026/3/22 11:13
 */
@Data
public class PositionUpdateRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 账户ID
     */
    private Long accountId;

    /**
     * 基金代码
     */
    private String code;

    /**
     * 持有份额
     */
    private BigDecimal shares;

    /**
     * 基金金额
     */
    private BigDecimal price;

    /**
     * 基金金额
     */
    private BigDecimal balance;

}
