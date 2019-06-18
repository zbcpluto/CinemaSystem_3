package com.example.cinema.data.management;

import com.example.cinema.po.RefundStrategy;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by AzureXH on 2019/6/9
 */
@Mapper
public interface RefundMapper {

    List<RefundStrategy> selectVipRefStra();

    List<RefundStrategy> selectNonVipRefStra();
    
    void insertRefundStrategy(List<RefundStrategy> refundStrategyList);

	void deleteRefundStrategyByState(int state);

	double selectRefundStrategyByState(int state);
	
}
