package com.wen.model.dto;

import lombok.Data;

/**
 * @author : rjw
 * @date : 2026-03-12
 */
@Data
public class FundValUserInfoDto {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

}
