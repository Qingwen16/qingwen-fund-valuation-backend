package com.wen.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : rjw
 * @date : 2026-03-12
 */
@Data
public class FundValUserFundRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 基金名字
     */
    private String name;

    /**
     * 基金代码
     */
    private String code;

    /**
     * 基金类型
     */
    private String type;

    /**
     * 持有份额
     */
    private BigDecimal amount;

}
