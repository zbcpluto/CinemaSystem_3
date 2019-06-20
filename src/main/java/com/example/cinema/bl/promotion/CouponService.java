package com.example.cinema.bl.promotion;

import java.util.List;

import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;

/**
 * Created by liying on 2019/4/17.
 */
public interface CouponService {

    /**
     * 获取相应用户的全部优惠券
     * @param userId
     * @return
     */
	ResponseVO getCouponsByUser(int userId);

    /**
     * 发布新的优惠券
     * @param couponForm
     * @return
     */
    ResponseVO addCoupon(CouponForm couponForm);

    /**
     * 给某个用户一张优惠券
     * @param couponId
     * @param userId
     * @return
     */
    ResponseVO issueCoupon(int couponId,int userId);

    /**
     * 根据消费金额筛选用户
     * @param ConsumptionBottom
     * @return
     */
    ResponseVO getUserByConsumption(double ConsumptionBottom);

    /**
     * 赠送优惠券
     * @param users
     * @param couponId
     * @return
     */
	ResponseVO giveCoupon(List<UserVO> users, int couponId);
	
}