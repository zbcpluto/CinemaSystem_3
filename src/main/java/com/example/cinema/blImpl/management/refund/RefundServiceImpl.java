package com.example.cinema.blImpl.management.refund;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cinema.bl.management.RefundService;
import com.example.cinema.data.management.RefundMapper;
import com.example.cinema.po.RefundStrategy;
import com.example.cinema.vo.RefStraVO;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;

/**
 * Created by AzureXH on 2019/6/8
 */
@Service
public class RefundServiceImpl implements RefundService {
    @Autowired
    private RefundMapper refundMapper;
    
    /**
     * 会员的退票策略
     */
    @Override
	public ResponseVO getVipRefStra() {
		try{
            List<RefundStrategy> refStraList = refundMapper.selectVipRefStra();
            RefStraVO rsv = refList2refVO(refStraList);
            return ResponseVO.buildSuccess(rsv);
        } catch(Exception e) {
        	e.printStackTrace();
            return  ResponseVO.buildFailure("获得会员退票策略失败");
        }
	}

    /**
     * 非会员的退票策略
     */
    @Override
	public ResponseVO getNonVipRefStra() {
    	try {
    		List<RefundStrategy> refStraList = refundMapper.selectNonVipRefStra();
            RefStraVO rsv = refList2refVO(refStraList);
            return ResponseVO.buildSuccess(rsv);
        } catch(Exception e) {
        	e.printStackTrace();
            return  ResponseVO.buildFailure("获得非会员退票策略失败");
        }
	}
    
    /**
     * 发布退票策略
     */
    @Override
    public ResponseVO addRefundStrategy(RefundForm refundForm) {
        try {
            List<RefundStrategy> refundStrategies = convert2List(refundForm);
            refundMapper.insertRefundStrategy(refundStrategies);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseVO.buildFailure("发布退票策略失败");
        }
    }

    @Override
    public ResponseVO deleteRefundStrategyByState(int state) {
        try {
        	refundMapper.deleteRefundStrategyByState(state);
            return ResponseVO.buildSuccess();
        } catch(Exception e) {
            return ResponseVO.buildFailure("删除退票策略失败");
        }
    }

    /**
     * 将数据库传上来的POList转为给页面层的VO
     * @param refundStrategyList
     * @return
     */
    private RefStraVO refList2refVO(List<RefundStrategy> refStraList) {
        if(refStraList.size() == 0) {
        	return null;
        }
        
        //添加共有属性
        RefStraVO rsv = new RefStraVO();
        RefundStrategy rs = refStraList.get(0);
        rsv.setName(rs.getName());
        rsv.setIsVip(rs.getIsVip());
        rsv.setFreeTime(rs.getFreeTime());
        rsv.setFalseTime(rs.getFalseTime());
        //添加策略
        List<String> startTimeList = new ArrayList<String>();
        List<String> endTimeList = new ArrayList<String>();
        List<Double> penaltyList = new ArrayList<Double>();
        for(RefundStrategy rs2: refStraList) {
        	startTimeList.add(rs2.getStartTime());
        	endTimeList.add(rs2.getEndTime());
        	penaltyList.add(rs2.getPenalty());
        }
        rsv.setStartTime(startTimeList);
        rsv.setEndTime(endTimeList);
        rsv.setPenalty(penaltyList);
        
        return rsv;
    }

    /**
     * 将页面层的单个VO转化为给数据库的POList
     * @param refundForm
     * @return
     */
    private List<RefundStrategy> convert2List(RefundForm refundForm) {
        List<RefundStrategy> refundStrategies = new ArrayList<RefundStrategy>();
        int len = refundForm.getStartTime().size();
        String name = refundForm.getName();
        String freeTime = refundForm.getFreeTime();
        String falseTime = refundForm.getFalseTime();
        int isVip = refundForm.getIsVip();
        for (int i = 0; i < len; i++) {
            String startTime = refundForm.getStartTime().get(i);
            String endTime = refundForm.getEndTime().get(i);
            double penalty = refundForm.getPenalty().get(i);
            refundStrategies.add(new RefundStrategy(name, isVip, freeTime, falseTime, startTime, endTime, penalty));
        }
        return refundStrategies;
    }

}
