package com.wen.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : 青灯文案
 * @Date: 2026/3/22 11:13
 */
@Data
public class IncreasePositionRequest {

    /**
     * 用户ID
     */
    private Long userId;




    /**
     * 基金代码
     */
    private String code;

    /**
     * 持有份额
     */
    private BigDecimal amount;

    /**
     * 基金金额
     */
    private BigDecimal balance;

}
