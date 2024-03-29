package com.example.cinema.blImpl.promotion;
import com.example.cinema.po.Coupon;

public interface CouponServiceForBl {

    /**
     * 根据id查找优惠券信息
     * @param id
     * @return
     */
    Coupon getCouponById(int id);
    
    void addCouponUser(int couponId, int userId);

    void deleteCouponUser(int couponId, int userId);

    boolean existCouponUser(int couponId, int userId);
    
}

