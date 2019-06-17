package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @GetMapping("{userId}/get")
    public ResponseVO getCoupons(@PathVariable int userId){
        return couponService.getCouponsByUser(userId);
    }

    @GetMapping("/get/user/{consumption}")
    public ResponseVO getUserByConsumption(@PathVariable Double consumption){
        return couponService.getUserByConsumption(consumption);
    }

	//    @GetMapping("不知道怎么同时得到users和couponId两个参数")
    @RequestMapping(value = "/give/{couponId}", method = RequestMethod.POST)
    public ResponseVO giveCoupon(@RequestBody List<UserVO> users, @PathVariable int couponId){
        return couponService.giveCoupon(users,couponId);
    }

}
