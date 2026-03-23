package com.wen.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : 青灯文案
 * @Date: 2026/3/22 11:13
 */
@Data
public class SharesRequest {

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

}
