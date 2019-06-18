package com.example.cinema.controller.management;

import com.example.cinema.bl.management.RefundService;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.RefundRatioForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by AzureXH on 2019/6/8
 */
@RestController
@RequestMapping("/refund")
public class RefundController {
    @Autowired
    private RefundService refundService;

    /**
     * 获得会员的退票策略
     */
    @RequestMapping(value = "/get/vip", method = RequestMethod.GET)
    public ResponseVO getVipRefStra() {
        return refundService.getVipRefStra();
    }
    
    /**
     * 获得非会员的退票策略
     */
    @RequestMapping(value = "/get/nonVip", method = RequestMethod.GET)
    public ResponseVO getNonVipRefStra() {
        return refundService.getNonVipRefStra();
    }
    
    /**
     * 添加一个退票策略
     * @param refundForm
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseVO addRefundStrategy(@RequestBody RefundForm refundForm){
        return refundService.addRefundStrategy(refundForm);
    }

    /**
     * 根据类型删除退票策略
     * @param state  1：会员   0：非会员
     * @return
     */
    @RequestMapping(value = "/delete/{state}", method = RequestMethod.GET)
    public ResponseVO deleteRefundStrategy(@PathVariable int state) {
        return refundService.deleteRefundStrategyByState(state);
    }
    
    /**
     * 获取当前时间的退票折算系数
     * @param state  1：会员   0：非会员
     * @return
     */
    @RequestMapping(value = "/get/ratio", method = RequestMethod.POST)
    public ResponseVO getRefStraRatio(@RequestBody RefundRatioForm rrf) {
        return refundService.getRefStraRatio(rrf);
    }
    
}
