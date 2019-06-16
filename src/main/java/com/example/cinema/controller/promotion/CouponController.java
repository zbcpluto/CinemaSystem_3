package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @GetMapping("/user/getByUser{userId}")
    public ResponseVO getCoupons(@PathVariable int userId){
        return couponService.getCouponUserByUserId(userId);
    }

    @GetMapping("{ConsumptionBottom}/getUser")
    public ResponseVO getUserByConsumption(@PathVariable Double ConsumptionBottom){
        return couponService.getUserByConsumption(ConsumptionBottom);
    }

	+//    @GetMapping("不知道怎么同时得到users和couponId两个参数")
    public ResponseVO giveCoupon(@PathVariable List<UserVO> users, @PathVariable int couponId){
        return couponService.giveCoupon(users,couponId);
    }

}
