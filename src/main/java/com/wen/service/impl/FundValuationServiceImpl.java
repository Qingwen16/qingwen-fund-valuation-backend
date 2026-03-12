package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wen.mapper.FundValFundInfoMapper;
import com.wen.mapper.FundValUserInfoMapper;
import com.wen.model.entity.FundValFundInfo;
import com.wen.model.entity.FundValUserFund;
import com.wen.model.entity.FundValUserInfo;
import com.wen.model.vo.FundValUserFundRequest;
import com.wen.model.vo.FundValUserIdRequest;
import com.wen.model.vo.FundValUserInfoRequest;
import com.wen.service.FundValuationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : rjw
 * @date : 2026-03-12
 */
public class FundValuationServiceImpl implements FundValuationService {

    @Autowired
    private FundValUserInfoMapper fundValUserInfoMapper;

    @Autowired
    private FundValFundInfoMapper fundValFundInfoMapper;

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
    }

    @Override
    public void deleteUserInfo(FundValUserIdRequest request) {
        if (request.getUserId() == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        fundValUserInfoMapper.deleteById(request.getUserId());
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

        FundValUserFund userFund = new FundValUserFund();
        userFund.setName(request.getName());
        userFund.setCode(request.getCode());
        userFund.set
    }

    @Override
    public void insertSelectionFundInfo(FundValUserFundRequest request) {
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            throw new RuntimeException("基金代码不能为空");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new RuntimeException("基金名称不能为空");
        }
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

        FundValUserFund userFund = new FundValUserFund();
        userFund.setName(request.getName());
        userFund.setCode(request.getCode());
        userFund.set

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