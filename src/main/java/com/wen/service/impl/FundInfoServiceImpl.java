package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.mapper.FundInfoMapper;
import com.wen.model.dto.FundInfoDto;
import com.wen.model.entity.FundInfo;
import com.wen.model.vo.UserFundRequest;
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
    public void insertFundInfo(FundInfoDto request) {
        FundInfo fundInfo = new FundInfo();
        fundInfo.setCode(request.getCode());
        fundInfo.setName(request.getName());
        fundInfo.setType(request.getType());
        fundInfo.setCompany(request.getCompany());
        fundInfo.setSection(request.getSection());
        fundInfo.setCreateTime(System.currentTimeMillis());
        fundInfoMapper.insert(fundInfo);
        log.info("新增基金：FundInfoDto：[{}]", fundInfo);
    }

    @Override
    public void insertFundInfo(UserFundRequest request) {
        // 1. 检查基金是否存在
        FundInfo fundInfo = fundInfoMapper.selectOne(new LambdaQueryWrapper<FundInfo>()
                .eq(FundInfo::getCode, request.getCode()));
        if (fundInfo == null) {
            fundInfo = new FundInfo();
            fundInfo.setName(request.getName());
            fundInfo.setCode(request.getCode());
            fundInfo.setType(request.getType());
            fundInfo.setSection(request.getSection());
            fundInfo.setCompany(request.getCompany());
            fundInfo.setCreateTime(System.currentTimeMillis());
            fundInfoMapper.insert(fundInfo);
            log.info("新增基金：UserFundRequest：[{}]", fundInfo);
        }else {
            log.info("新增基金：UserFundRequest：基金已存在[{}]", fundInfo);
        }
    }
}
