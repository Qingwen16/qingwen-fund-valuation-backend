package com.wen.controller;

import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.entity.FundInfo;
import com.wen.model.vo.FundCodeRequest;
import com.wen.service.FundInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-31
 */
@RestController
@RequestMapping("/fund/valuation")
@RequiredArgsConstructor
public class FundInfoController {

    private final FundInfoService fundInfoService;

    /**
     * 获取用户所有自选基金列表
     */
    @PostMapping("/queryFundInfo")
    public Response<FundInfo> queryUserWatchlistFunds(@RequestBody FundCodeRequest request) {
        if (request == null || request.getCode() == null) {
            throw new BusinessException("输入参数不能为空");
        }
        FundInfo responses = fundInfoService.queryFundInfo(request.getCode());
        return Response.success(responses);
    }

}
