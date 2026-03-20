package com.wen.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Data
public class UptFundRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 基金代码
     */
    private String code;

    /**
     * 基金金额
     */
    private BigDecimal balance;

}
