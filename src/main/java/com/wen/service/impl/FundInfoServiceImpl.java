package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.common.exception.BusinessException;
import com.wen.mapper.FundInfoMapper;
import com.wen.model.entity.FundInfo;
import com.wen.service.FundInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FundInfoServiceImpl implements FundInfoService {

    private final FundInfoMapper fundInfoMapper;

    @Override
    public void insertFundInfo(FundInfo request) {
        // 1. 检查基金是否存在
        FundInfo fundInfo = fundInfoMapper.selectOne(new LambdaQueryWrapper<FundInfo>());
        if (fundInfo != null) {
            log.info("新增基金：FundInfoDto：基金已存在[{}]", fundInfo);
            return;
        }
        fundInfoMapper.insert(request);
        log.info("新增基金：FundInfoDto：[{}]", request);
    }

    @Override
    public void deleteFundInfo(String code) {
        if (code == null){
            log.info("删除基金：code：参数错误");
            throw new BusinessException("删除基金：code：参数错误");
        }
        LambdaQueryWrapper<FundInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundInfo::getCode, code);
        fundInfoMapper.delete(wrapper);
        log.info("删除基金：code：[{}]", code);
    }
}
