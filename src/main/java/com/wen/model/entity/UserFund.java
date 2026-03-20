package com.wen.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : rjw
 * @date : 2026-03-12
 */
@TableName("t_user_fund")
@Data
public class UserFund {

    /**
     * ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

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
     * 持有份额
     */
    private BigDecimal amount;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 创建时间
     */
    private Long createTime;

}
