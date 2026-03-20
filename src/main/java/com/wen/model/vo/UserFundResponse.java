package com.wen.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Data
public class UserFundResponse {

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
     * 是否持有
     */
    private Integer holding;

    /**
     * 是否自选
     */
    private Integer selection;

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
    private BigDecimal amount;

}


