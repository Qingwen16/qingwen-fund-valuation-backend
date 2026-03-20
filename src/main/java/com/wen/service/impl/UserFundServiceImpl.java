package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.common.constant.UserFundDelEnum;
import com.wen.common.exception.BusinessException;
import com.wen.mapper.FundInfoMapper;
import com.wen.mapper.UserFundMapper;
import com.wen.model.entity.FundInfo;
import com.wen.model.entity.UserFund;
import com.wen.model.vo.UserFundRequest;
import com.wen.model.vo.UserFundResponse;
import com.wen.model.vo.UserIdRequest;
import com.wen.service.FundInfoService;
import com.wen.service.UserFundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserFundServiceImpl implements UserFundService {

    private final FundInfoService fundInfoService;

    private final UserFundMapper userFundMapper;

    private final FundInfoMapper fundInfoMapper;

    @Override
    public List<UserFundResponse> getUserSelectionFunds(UserIdRequest request) {
        if (request.getUserId() == null) {
            throw new BusinessException("GetUserSelectionFunds：输入参数用户ID为空");
        }

        List<UserFund> userFundList = userFundMapper.selectList(new LambdaQueryWrapper<UserFund>()
                .eq(UserFund::getUserId, request.getUserId())
                .eq(UserFund::getSelection, 1)
                .eq(UserFund::getDeleted, UserFundDelEnum.NORMAL.getCode()));

        Set<String> codeSet = userFundList.stream().map(UserFund::getCode).collect(Collectors.toSet());

        List<FundInfo> fundInfoList = fundInfoMapper.selectList(new LambdaQueryWrapper<FundInfo>()
                .in(FundInfo::getCode, codeSet));

        List<UserFundResponse> responseList = getUserFundResponses(userFundList, fundInfoList);
        log.info("获取用户自选基金: 基金数量: [{}]", responseList.size());
        return responseList;
    }

    @Override
    public List<UserFundResponse> getUserHoldingFunds(UserIdRequest request) {
        if (request.getUserId() == null) {
            throw new BusinessException("GetUserSelectionFunds：输入参数用户ID为空");
        }

        List<UserFund> userFundList = userFundMapper.selectList(new LambdaQueryWrapper<UserFund>()
                .eq(UserFund::getUserId, request.getUserId())
                .eq(UserFund::getHolding, 1)
                .eq(UserFund::getDeleted, UserFundDelEnum.NORMAL.getCode()));

        Set<String> codeSet = userFundList.stream().map(UserFund::getCode).collect(Collectors.toSet());

        List<FundInfo> fundInfoList = fundInfoMapper.selectList(new LambdaQueryWrapper<FundInfo>()
                .in(FundInfo::getCode, codeSet));

        List<UserFundResponse> responseList = getUserFundResponses(userFundList, fundInfoList);
        log.info("获取用户持有基金: 基金数量: [{}]", responseList.size());
        return responseList;
    }

    private List<UserFundResponse> getUserFundResponses(List<UserFund> userFundList, List<FundInfo> fundInfoList) {
        List<UserFundResponse> responseList = new ArrayList<>();

        for (UserFund userFund : userFundList) {
            for (FundInfo fundInfo : fundInfoList) {
                if (Objects.equals(fundInfo.getCode(), userFund.getCode())) {
                    UserFundResponse response = new UserFundResponse();
                    response.setUserId(userFund.getUserId());
                    response.setName(fundInfo.getName());
                    response.setCode(fundInfo.getCode());
                    response.setHolding(userFund.getHolding());
                    response.setSelection(userFund.getSelection());
                    response.setType(fundInfo.getType());
                    response.setCompany(fundInfo.getCompany());
                    response.setSection(fundInfo.getSection());
                    response.setAmount(userFund.getAmount());
                    responseList.add(response);
                }
            }
        }
        return responseList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSelectionFund(UserFundRequest request) {
        if (request.getUserId() == null) {
            throw new BusinessException("AddSelectionFund：输入参数用户ID为空");
        }
        if (request.getCode() == null) {
            throw new BusinessException("AddSelectionFund：输入参数基金code为空");
        }
        long currentTime = System.currentTimeMillis();
        log.info("新增自选基金：[{}]", request);

        // 1、基金入库
        fundInfoService.insertFundInfo(request);

        // 2. 写入用户基金关系表
        UserFund userFund = userFundMapper.selectOne(new LambdaQueryWrapper<UserFund>()
                .eq(UserFund::getUserId, request.getUserId())
                .eq(UserFund::getCode, request.getCode()));
        if (userFund == null) {
            userFund = new UserFund();
            userFund.setUserId(request.getUserId());
            userFund.setCode(request.getCode());
            userFund.setCreateTime(currentTime);
            userFund.setHolding(0);
            userFund.setDeleted(UserFundDelEnum.NORMAL.getCode());
            userFundMapper.insert(userFund);
        }
        userFund.setSelection(1);
        userFund.setUpdateTime(currentTime);
        userFund.setDeleted(UserFundDelEnum.NORMAL.getCode());
        log.info("新增自选基金：新增用户基金关系：[{}]", userFund);
    }

    @Override
    public void addHoldingFund(UserFundRequest request) {
        if (request.getUserId() == null) {
            throw new BusinessException("AddHoldingFund：输入参数用户ID为空");
        }
        if (request.getCode() == null) {
            throw new BusinessException("AddHoldingFund：输入参数基金code为空");
        }
        if (request.getAmount() == null) {
            throw new BusinessException("AddHoldingFund：输入参数基金持有份数为空");
        }

        long currentTime = System.currentTimeMillis();
        log.info("新增持有基金：[{}]", request);

        // 1、基金入库
        fundInfoService.insertFundInfo(request);

        // 2. 写入用户基金关系表
        UserFund userFund = userFundMapper.selectOne(new LambdaQueryWrapper<UserFund>()
                .eq(UserFund::getUserId, request.getUserId())
                .eq(UserFund::getCode, request.getCode()));
        if (userFund == null) {
            userFund = new UserFund();
            userFund.setUserId(request.getUserId());
            userFund.setCode(request.getCode());
            userFund.setDeleted(UserFundDelEnum.NORMAL.getCode());
            userFund.setCreateTime(currentTime);
            userFund.setSelection(0);
            userFundMapper.insert(userFund);
        }
        userFund.setHolding(1);
        userFund.setAmount(request.getAmount());
        userFund.setUpdateTime(currentTime);
        userFund.setDeleted(UserFundDelEnum.NORMAL.getCode());
        log.info("新增持有基金：新增用户基金关系：[{}]", userFund);
    }


}
