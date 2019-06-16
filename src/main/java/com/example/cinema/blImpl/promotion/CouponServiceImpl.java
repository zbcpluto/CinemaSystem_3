package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.po.Coupon;
import com.example.cinema.po.User;
import com.example.cinema.vo.UserVO;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liying on 2019/4/17.
 */
@Service
public class CouponServiceImpl implements CouponService, CouponServiceForBl {

    @Autowired
    CouponMapper couponMapper;

    @Override
    public ResponseVO getCouponsByUser(int userId) {
        try {
            return ResponseVO.buildSuccess(couponMapper.selectCouponByUser(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addCoupon(CouponForm couponForm) {
        try {
            Coupon coupon=new Coupon();
            coupon.setName(couponForm.getName());
            coupon.setDescription(couponForm.getDescription());
            coupon.setTargetAmount(couponForm.getTargetAmount());
            coupon.setDiscountAmount(couponForm.getDiscountAmount());
            coupon.setStartTime(couponForm.getStartTime());
            coupon.setEndTime(couponForm.getEndTime());
            couponMapper.insertCoupon(coupon);
            return ResponseVO.buildSuccess(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO issueCoupon(int couponId, int userId) {
        try {
            couponMapper.insertCouponUser(couponId,userId);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public Coupon getCouponById(int couponId){
        try{
            return couponMapper.selectById(couponId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteCoupon(int couponId, int userId){
        try{
            couponMapper.deleteCouponUser(couponId,userId);

        }catch (Exception e){
            e.printStackTrace();
        }

        return;
    }

    @Override
    public boolean existCouponUser(int couponId, int userId){
        try{
            List<Coupon> coupons = couponMapper.selectCouponByUser(userId);
            for (Coupon coupon: coupons){
                if(couponId == coupon.getId()){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ResponseVO getUserByConsumption(double consumptionBottom){
        try {
            List<User> result=couponMapper.selectUserByTicketConsumption(consumptionBottom);
            if (result.size()!=0){
                return ResponseVO.buildSuccess(result);
            }
            return ResponseVO.buildFailure("暂时没有消费超过该底线的用户");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO giveCoupon(List<UserVO> users, int couponId){
        try {
            for(UserVO user: users){
                couponMapper.addCoupon(couponId,user.getId());
            }
            return ResponseVO.buildSuccess("添加优惠券成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
