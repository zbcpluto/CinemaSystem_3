package com.example.cinema.controller.management;

import com.example.cinema.bl.management.RefundService;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by AzureXH on 2019/6/8
 */
@RestController
public class RefundController {
    @Autowired
    private RefundService refundService;

    /**
     * 添加一个退票策略
     * @param refundForm
     * @return
     */
    @RequestMapping(value = "/refund/add",method = RequestMethod.POST)
    public ResponseVO addRefundStrategy(@RequestBody RefundForm refundForm){
        return refundService.addRefundStrategy(refundForm);
    }

    /**
     * 得到数据库中所有的退票策略
     * @return
     */
    @RequestMapping(value = "/refund/manage",method = RequestMethod.GET)
    public ResponseVO getAllRefundStrategy() {
        return refundService.getAllRefundStrategy();
    }

    /**
     * 依据名称删除某个退票策略
     * @param name
     * @return
     */
    @RequestMapping(value = "/refund/delete/{name}", method = RequestMethod.GET)
    public ResponseVO deleteRefundStrategy(@PathVariable String name){
        return refundService.deleteRefundStrategyByName(name);
    }

    @RequestMapping(value = "/refund/update" , method = RequestMethod.POST)
    public ResponseVO updateRefundStrategy(@RequestBody RefundForm refundForm){
        return refundService.updateRefundStrategy(refundForm);
    }

}
