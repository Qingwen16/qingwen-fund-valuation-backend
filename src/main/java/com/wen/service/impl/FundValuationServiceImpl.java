package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wen.mapper.FundValFundInfoMapper;
import com.wen.mapper.FundValUserFundMapper;
import com.wen.mapper.FundValUserInfoMapper;
import com.wen.model.entity.FundValFundInfo;
import com.wen.model.entity.FundValUserFund;
import com.wen.model.entity.FundValUserInfo;
import com.wen.model.vo.FundValUserFundRequest;
import com.wen.model.vo.FundValUserIdRequest;
import com.wen.model.vo.FundValUserInfoRequest;
import com.wen.service.FundValuationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : rjw
 * @date : 2026-03-12
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FundValuationServiceImpl implements FundValuationService {

    private FundValUserInfoMapper fundValUserInfoMapper;

    private FundValFundInfoMapper fundValFundInfoMapper;

    private FundValUserFundMapper fundValUserFundMapper;

    @Override
    public FundValUserInfo queryUserInfo(FundValUserIdRequest request) {
        if (request.getUserId() == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        return fundValUserInfoMapper.selectById(request.getUserId());
    }

    @Override
    public void updateUserInfo(FundValUserInfoRequest request) {
        if (request.getUserId() == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        FundValUserInfo userInfo = fundValUserInfoMapper.selectById(request.getUserId());
        if (userInfo == null) {
            throw new RuntimeException("用户信息不存在");
        }
        fundValUserInfoMapper.update(null, new LambdaUpdateWrapper<FundValUserInfo>()
                .eq(FundValUserInfo::getUserId, request.getUserId())
                .set(request.getUsername() != null, FundValUserInfo::getUsername, request.getUsername())
                .set(request.getEmail() != null, FundValUserInfo::getEmail, request.getEmail())
                .set(request.getPhone() != null, FundValUserInfo::getPhone, request.getPhone())
                .set(FundValUserInfo::getUpdateTime, System.currentTimeMillis())
        );
        log.info("更新用户信息成功：{}", userInfo);
    }

    @Override
    public void deleteUserInfo(FundValUserIdRequest request) {
        if (request.getUserId() == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        fundValUserInfoMapper.deleteById(request.getUserId());
        log.info("删除用户信息成功：{}", request.getUserId());
    }

    /**
     * 新增持有基金数据
     */
    @Override
    public void insertHoldingFundInfo(FundValUserFundRequest request) {
        if (request.getUserId() == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        if (request.getAmount() == null) {
            throw new RuntimeException("基金份额不能为空");
        }
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            throw new RuntimeException("基金代码不能为空");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new RuntimeException("基金名称不能为空");
        }
        insertFundInfo(request);
        insertUserFundRelation(request, 0);
    }

    @Override
    public void insertSelectionFundInfo(FundValUserFundRequest request) {
        if (request.getUserId() == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            throw new RuntimeException("基金代码不能为空");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new RuntimeException("基金名称不能为空");
        }
        insertFundInfo(request);
        insertUserFundRelation(request, 1);
    }

    private void insertFundInfo(FundValUserFundRequest request) {
        // 检查基金代码是否已存在
        FundValFundInfo existFund = fundValFundInfoMapper.selectOne(new LambdaQueryWrapper<FundValFundInfo>()
                .eq(FundValFundInfo::getCode, request.getCode())
        );
        if (existFund != null) {
            throw new RuntimeException("基金代码已存在");
        }
        FundValFundInfo fundInfo = new FundValFundInfo();
        fundInfo.setName(request.getName());
        fundInfo.setCode(request.getCode());
        fundInfo.setType(request.getType());
        fundInfo.setCreateTime(System.currentTimeMillis());
        fundValFundInfoMapper.insert(fundInfo);
        log.info("新增基金信息成功：{}", fundInfo);
    }

    private void insertUserFundRelation(FundValUserFundRequest request, int num) {
        FundValUserFund userFund = new FundValUserFund();
        userFund.setUserId(request.getUserId());
        userFund.setCode(request.getCode());
        if (num == 0) {
            userFund.setHolding(1);
        }
        if (num == 1) {
            userFund.setSelection(1);
        }
        userFund.setAmount(request.getAmount());
        userFund.setDeleted(0);
        userFund.setCreateTime(System.currentTimeMillis());
        userFund.setUpdateTime(System.currentTimeMillis());
        fundValUserFundMapper.insert(userFund);
        log.info("新增用户基金关系成功：{}", userFund);
    }

    @Override
    public void deleteFundInfo(FundValUserFundRequest request) {
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            throw new RuntimeException("基金代码不能为空");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new RuntimeException("基金名称不能为空");
        }
        fundValFundInfoMapper.delete(new LambdaQueryWrapper<FundValFundInfo>()
                .eq(FundValFundInfo::getCode, request.getCode())
        );
        log.info("删除基金信息成功：{}", request.getCode());
    }

    /**
     * 查询用户持有的基金
     */
    @Override
    public void queryUserFundHolding(FundValUserIdRequest request) {

    }

    /**
     * 查询用户自选的基金
     */
    @Override
    public void queryUserFundSelection(FundValUserIdRequest request) {

    }
}