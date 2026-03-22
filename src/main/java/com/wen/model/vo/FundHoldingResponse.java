package com.wen.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : 青灯文案
 * @Date: 2026/3/22 12:48
 */
@Data
public class FundHoldingResponse {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 账户ID
     */
    private Long accountId;

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
     * 基金公司
     */
    private String company;

    /**
     * 基金版块
     */
    private String section;

    /**
     * 持有份额
     */
    private BigDecimal shares;

}
