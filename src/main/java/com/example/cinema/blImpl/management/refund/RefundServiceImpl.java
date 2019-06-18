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
import com.example.cinema.vo.RefundRatioForm;
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
        	e.printStackTrace();
            return ResponseVO.buildFailure("删除退票策略失败");
        }
    }
    
    @Override
	public ResponseVO getRefStraRatio(RefundRatioForm rrf) {
    	try {
    		List<RefundStrategy> refStras;
    		if(rrf.getIsVip() == 1) refStras = refundMapper.selectVipRefStra();
    		else 					  refStras = refundMapper.selectNonVipRefStra();
    		if(refStras.size() == 0) {
    			return ResponseVO.buildFailure("暂不支持本次退票");
    		}
    		String[] freeTime = refStras.get(0).getFreeTime().split(":");
    		String[] falseTime = refStras.get(0).getFalseTime().split(":");
    		int free_min = Integer.parseInt(freeTime[0]);
    		int free_sec = Integer.parseInt(freeTime[1]);
    		long free_span = free_min * 3600 * 1000 + free_sec * 60 * 1000;
    		int false_min = Integer.parseInt(falseTime[0]);
    		int false_sec = Integer.parseInt(falseTime[1]);
    		long false_span = false_min * 3600 * 1000 + false_sec * 60 * 1000;
    		long span =  rrf.getStartTime().getTime() - System.currentTimeMillis();
    		if(span <= false_span) {
    			StringBuilder sb = new StringBuilder("电影放映前");
    			sb.append(false_min == 0 ? "":(false_min+"小时"));
    			sb.append(false_sec == 0 ? "":(false_sec+"分钟"));
    			sb.append("内不可退票！");
    			return ResponseVO.buildFailure(sb.toString());
    		}
    		else if(span > free_span) {
    			return ResponseVO.buildSuccess(0);
    		}
    		else {
    			List<Double> res = new ArrayList<Double>();
    			for(RefundStrategy refStra: refStras) {
    				String[] startTime = refStra.getStartTime().split(":");
    	    		String[] endTime = refStra.getEndTime().split(":");
    	    		int start_min = Integer.parseInt(startTime[0]);
    	    		int start_sec = Integer.parseInt(startTime[1]);
    	    		long start_span = start_min * 3600 * 1000 + start_sec * 60 * 1000;
    	    		int end_min = Integer.parseInt(endTime[0]);
    	    		int end_sec = Integer.parseInt(endTime[1]);
    	    		long end_span = end_min * 3600 * 1000 + end_sec * 60 * 1000;
    	    		if(span > start_span && span < end_span) {
    	    			res.add(refStra.getPenalty());
    	    		}
    			}
    			if(res.size() == 0) {
    				return ResponseVO.buildFailure("暂不支持本次退票");
    			}
    			else {
    				return ResponseVO.buildSuccess(res.get(0));
    			}
    		}
        } catch(Exception e) {
        	e.printStackTrace();
            return ResponseVO.buildFailure("获取退票折算比失败");
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
