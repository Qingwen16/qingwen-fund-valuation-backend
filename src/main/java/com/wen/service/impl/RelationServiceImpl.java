package com.wen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wen.common.exception.BusinessException;
import com.wen.mapper.FundHoldingMapper;
import com.wen.mapper.FundInfoMapper;
import com.wen.mapper.FundWatchlistMapper;
import com.wen.model.dto.FundHoldingDto;
import com.wen.model.dto.FundWatchlistDto;
import com.wen.model.entity.FundHolding;
import com.wen.model.entity.FundInfo;
import com.wen.model.entity.FundWatchlist;
import com.wen.model.vo.*;
import com.wen.service.FundService;
import com.wen.service.RelationService;
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
public class RelationServiceImpl implements RelationService {

    private final FundService fundService;

    private final FundHoldingMapper fundHoldingMapper;

    private final FundWatchlistMapper fundWatchlistMapper;

    private final FundInfoMapper fundInfoMapper;

    /**
     * 获取用户自选基金
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<FundWatchlistDto> getUserWatchlistFunds(UserIdRequest request) {
        // 1. 获取用户自选基金
        if (request.getUserId() == null) {
            throw new BusinessException("[GetUserWatchlistFunds]: 输入参数用户ID为空");
        }
        // 2. 获取用户自选基金对应的基金信息
        LambdaQueryWrapper<FundWatchlist> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundWatchlist::getUserId, request.getUserId());
        List<FundWatchlist> watchlistList = fundWatchlistMapper.selectList(wrapper);
        // 3. 提取基金代码
        Set<String> codeSet = watchlistList.stream().map(FundWatchlist::getCode).collect(Collectors.toSet());
        // 4. 获取用户自选基金对应的基金信息
        List<FundInfo> fundInfoList = fundInfoMapper.selectList(new LambdaQueryWrapper<FundInfo>()
                .in(FundInfo::getCode, codeSet));
        // 5. 构建基金信息
        List<FundWatchlistDto> responseList = buildUserWatchlistFunds(watchlistList, fundInfoList);
        log.info("获取用户自选基金: 基金数量: [{}]", responseList.size());
        return responseList;
    }

    private List<FundWatchlistDto> buildUserWatchlistFunds(List<FundWatchlist> watchlistList, List<FundInfo> fundInfoList) {
        List<FundWatchlistDto> responseList = new ArrayList<>();
        for (FundWatchlist watchlist : watchlistList) {
            for (FundInfo fundInfo : fundInfoList) {
                if (Objects.equals(fundInfo.getCode(), watchlist.getCode())) {
                    FundWatchlistDto resp = new FundWatchlistDto();
                    resp.setUserId(watchlist.getUserId());
                    resp.setName(fundInfo.getName());
                    resp.setCode(fundInfo.getCode());
                    resp.setType(fundInfo.getType());
                    resp.setCompany(fundInfo.getCompany());
                    resp.setSection(fundInfo.getSection());
                    responseList.add(resp);
                }
            }
        }
        return responseList;
    }

    /**
     * 获取用户持有基金
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<FundHoldingDto> getUserHoldingFunds(AccountRequest request) {
        // 1. 校验用户账户信息
        if (request.getUserId() == null || request.getAccountId() == null) {
            throw new BusinessException("[GetUserHoldingFunds]: 输入参数用户ID为空 或 账户ID为空");
        }
        // 2. 获取用户持有基金
        LambdaQueryWrapper<FundHolding> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundHolding::getUserId, request.getUserId());
        wrapper.eq(FundHolding::getAccountId, request.getAccountId());
        List<FundHolding> holdingList = fundHoldingMapper.selectList(wrapper);
        // 3. 获取用户持有基金的代码
        Set<String> codeSet = holdingList.stream().map(FundHolding::getCode).collect(Collectors.toSet());
        // 4. 获取用户持有基金对应的基金信息
        List<FundInfo> fundInfoList = fundInfoMapper.selectList(new LambdaQueryWrapper<FundInfo>()
                .in(FundInfo::getCode, codeSet));
        // 5. 构建基金信息
        List<FundHoldingDto> responseList = buildUserHoldingFunds(holdingList, fundInfoList);
        log.info("获取用户持有基金: 基金数量: [{}]", responseList.size());
        return responseList;
    }

    private List<FundHoldingDto> buildUserHoldingFunds(List<FundHolding> holdingList, List<FundInfo> fundInfoList) {
        List<FundHoldingDto> responseList = new ArrayList<>();
        for (FundHolding holding : holdingList) {
            for (FundInfo fundInfo : fundInfoList) {
                if (Objects.equals(fundInfo.getCode(), holding.getCode())) {
                    FundHoldingDto resp = new FundHoldingDto();
                    resp.setUserId(holding.getUserId());
                    resp.setAccountId(holding.getAccountId());
                    resp.setName(fundInfo.getName());
                    resp.setCode(fundInfo.getCode());
                    resp.setType(fundInfo.getType());
                    resp.setCompany(fundInfo.getCompany());
                    resp.setSection(fundInfo.getSection());
                    resp.setShares(holding.getShares());
                    responseList.add(resp);
                }
            }
        }
        return responseList;
    }

    /**
     * 添加自选基金
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addWatchlistFund(FundWatchlistDto request) {
        if (request.getUserId() == null || request.getCode() == null) {
            throw new BusinessException("AddSelectionFund：输入参数用户ID为空或者基金code为空");
        }
        long currentTime = System.currentTimeMillis();
        log.info("新增自选基金：[{}]", request);
        // 1. 构建基金实体类
        FundInfo fundInfo = new FundInfo();
        fundInfo.setName(request.getName());
        fundInfo.setCode(request.getCode());
        fundInfo.setType(request.getType());
        fundInfo.setCompany(request.getCompany());
        fundInfo.setSection(request.getSection());
        fundInfo.setCreateTime(currentTime);
        // 2、基金入库
        fundService.insertFundInfo(fundInfo);
        // 3. 写入用户基金关系表
        LambdaQueryWrapper<FundWatchlist> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundWatchlist::getUserId, request.getUserId());
        wrapper.eq(FundWatchlist::getCode, request.getCode());
        if (fundWatchlistMapper.selectOne(wrapper) != null) {
            throw new BusinessException("AddSelectionFund：用户自选基金关系已存在");
        }
        FundWatchlist fundWatchlist = new FundWatchlist();
        fundWatchlist.setUserId(request.getUserId());
        fundWatchlist.setCode(request.getCode());
        fundWatchlist.setCreateTime(currentTime);
        fundWatchlistMapper.insert(fundWatchlist);
        log.info("新增自选基金：新增用户基金关系：[{}]", fundWatchlist);
    }

    /**
     * 添加持有基金
     */
    @Override
    public void addHoldingFund(FundHoldingDto request) {
        if (request.getUserId() == null|| request.getCode() == null || request.getShares() == null) {
            log.error("AddHoldingFund：输入参数: 用户ID为空 或 基金code为空 或 基金份额为空, [{}]",  request);
            throw new BusinessException("AddHoldingFund：输入参数: 用户ID为空 或 基金code为空 或 基金份额为空");
        }
        long currentTime = System.currentTimeMillis();
        log.info("新增持有基金：[{}]", request);
        // 1. 构建基金实体类
        FundInfo fundInfo = new FundInfo();
        fundInfo.setName(request.getName());
        fundInfo.setCode(request.getCode());
        fundInfo.setType(request.getType());
        fundInfo.setCompany(request.getCompany());
        fundInfo.setSection(request.getSection());
        fundInfo.setCreateTime(currentTime);
        // 2、基金入库
        fundService.insertFundInfo(fundInfo);
        // 3. 写入用户基金关系表
        LambdaQueryWrapper<FundHolding> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FundHolding::getUserId, request.getUserId());
        wrapper.eq(FundHolding::getAccountId, request.getAccountId());
        wrapper.eq(FundHolding::getCode, request.getCode());
        if (fundHoldingMapper.selectOne(wrapper) != null) {
            throw new BusinessException("AddSelectionFund：用户自选基金关系已存在");
        }
        FundHolding fundHolding = new FundHolding();
        fundHolding.setUserId(request.getUserId());
        fundHolding.setAccountId(request.getAccountId());
        fundHolding.setShares(request.getShares());
        fundHolding.setCode(request.getCode());
        fundHolding.setUpdateTime(currentTime);
        fundHolding.setCreateTime(currentTime);
        fundHoldingMapper.insert(fundHolding);
        log.info("新增自选基金：新增用户基金关系：[{}]", fundHolding);
    }

}
