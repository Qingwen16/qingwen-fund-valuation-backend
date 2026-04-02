package com.wen.controller;

import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.vo.PositionRequest;
import com.wen.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : rjw
 * @date : 2026-03-23
 * 基金仓位变化的接口
 */
@RestController
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    /**
     * 更新基金加仓数据
     */
    @PostMapping("/updatePosition")
    public Response<?> updatePosition(@RequestBody PositionRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        positionService.updatePosition(request);
        return Response.success();
    }

    /**
     * 持有基金加仓数据
     */
    @PostMapping("/increasePosition")
    public Response<?> increasePosition(@RequestBody PositionRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        positionService.increasePosition(request);
        return Response.success();
    }

    /**
     * 持有基金减仓数据
     */
    @PostMapping("/decreasePosition")
    public Response<?> decreasePosition(@RequestBody PositionRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        positionService.decreasePosition(request);
        return Response.success();
    }


}
