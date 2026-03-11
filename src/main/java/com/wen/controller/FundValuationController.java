package com.wen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : 青灯文案
 * @Date: 2026/3/9 23:13
 */
@RestController("/fund/valuation")
@Slf4j
public class FundValuationController {


    @PostMapping("queryUserFunds")
    public String queryUserFunds(){
        log.info("查询用户基金");
        return "查询用户基金成功";
    }

}