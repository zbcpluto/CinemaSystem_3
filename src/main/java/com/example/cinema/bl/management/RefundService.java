package com.example.cinema.bl.management;

import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.RefundRatioForm;
import com.example.cinema.vo.ResponseVO;

/**
 * Created by AzureXH on 2019/6/8
 */
public interface RefundService {
    /**
     * 得到会员的退票策略
     * @return
     */
    ResponseVO getVipRefStra();
    
    /**
     * 得到非会员的退票策略
     * @return
     */
    ResponseVO getNonVipRefStra();
    
    /**
     * 加一个退票策略
     * @param refundForm
     * @return
     */
    ResponseVO addRefundStrategy(RefundForm refundForm);

    /**
     * 删除一个退票策略
     * @param state
     * @return
     */
    ResponseVO deleteRefundStrategyByState(int state);

    /**
     * 获取退票策略的折算系数
     * @param rrf
     * @return
     */
	ResponseVO getRefStraRatio(RefundRatioForm rrf);

}
