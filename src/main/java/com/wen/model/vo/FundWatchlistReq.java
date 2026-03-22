package com.wen.model.vo;

import lombok.Data;

/**
 * @Author : 青灯文案
 * @Date: 2026/3/22 13:01
 */
@Data
public class FundWatchlistReq {

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
     * 基金公司
     */
    private String company;

    /**
     * 基金版块
     */
    private String section;

}
