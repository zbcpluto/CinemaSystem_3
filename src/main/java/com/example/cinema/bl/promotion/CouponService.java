package com.example.cinema.bl.promotion;

import java.util.List;

import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;

/**
 * Created by liying on 2019/4/17.
 */
public interface CouponService {
	
	ResponseVO getCouponUserByUserId(int userId);

    ResponseVO addCoupon(CouponForm couponForm);

    ResponseVO issueCoupon(int couponId,int userId);

    ResponseVO getUserByConsumption(double ConsumptionBottom);

	ResponseVO giveCoupon(List<UserVO> users, int couponId);
}