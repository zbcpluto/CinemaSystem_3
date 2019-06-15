package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;

    @PostMapping("/add")
    public ResponseVO addVIP(@RequestParam int userId, @RequestParam int serviceId){
        return vipService.addVIPCard(userId, serviceId);
    }
    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId){
        return vipService.getCardByUserId(userId);
    }

    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo(){
        return vipService.getVIPInfo();
    }
    
    @GetMapping("/getVIPInfo/{id}")
    public ResponseVO getVIPInfoById(@PathVariable int id){
        return vipService.getVIPInfoById(id);
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPChargeForm vipChargeForm){
        return vipService.charge(vipChargeForm);
    }

    @PostMapping("/publish")
    public ResponseVO publishVipcard(@RequestBody VIPInfoForm vipInfoForm){
        return vipService.publishVipcard(vipInfoForm);
    }

    @PostMapping("/update")
    public ResponseVO updateVipcard(@RequestBody VIPInfoForm vipInfoForm){
        return vipService.updateVipcard(vipInfoForm);
    }
    
    @GetMapping("chargeRecord/{userId}")
    public ResponseVO getChargeHistory(@PathVariable int userId) {
    	return vipService.getChargeHistory(userId);
    }
    
}
