package com.example.cinema.blImpl.promotion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.po.Coupon;
import com.example.cinema.po.User;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.CouponVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;

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
        	List<Coupon> couponList = couponMapper.selectCouponByUser(userId);
        	Map<Integer, CouponVO> map = new HashMap<>();
        	couponList.stream().forEach(coupon -> {
        		int id = coupon.getId();
        		if(!map.containsKey(id)) {
        			map.put(id, coupon.getVO());
        		}
        		map.get(id).addNum();
        	});
        	
        	List<CouponVO> res = new ArrayList<>();
        	map.values().stream().forEach(cv -> {
        		res.add(cv);
        	});
            return ResponseVO.buildSuccess(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addCoupon(CouponForm couponForm) {
        try {
            Coupon coupon = new Coupon();
            coupon.setName(couponForm.getName());
            coupon.setDescription(couponForm.getDescription());
            coupon.setTargetAmount(couponForm.getTargetAmount());
            coupon.setDiscountAmount(couponForm.getDiscountAmount());
            coupon.setStartTime(couponForm.getStartTime());
            coupon.setEndTime(couponForm.getEndTime());
            couponMapper.insertOneCoupon(coupon);
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
    public Coupon getCouponById(int couponId) {
        try {
            return couponMapper.selectCouponById(couponId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
	public void addCouponUser(int couponId, int userId) {
    	try {
            couponMapper.insertCouponUser(couponId, userId);
        } catch (Exception e){
            e.printStackTrace();
        }
	}

    @Override
    public void deleteCouponUser(int couponId, int userId) {
    	try {
            couponMapper.deleteCouponUser(couponId, userId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /*@Override
    public boolean existCouponUser(int couponId, int userId) {
        try{
            List<CouponUser> cuList = couponMapper.selectCouponUserByUserId(userId);
            for (CouponUser cu: cuList) {
                if(cu.getCouponId() == couponId){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    } */
    
    @Override
    public boolean existCouponUser(int couponId, int userId){
        try{
            List<Coupon> coupons = couponMapper.selectCouponByUser(userId);
            for (Coupon coupon: coupons){
                //System.out.println("size: "+coupons.size());
                //System.out.println("userid"+userId);
                //System.out.println("1`"+couponId+" "+coupon.getId());
                if(couponId == coupon.getId()){
                    //System.out.println("2`"+couponId+" "+coupon.getId());
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
                couponMapper.insertCouponUser(couponId, user.getId());
            }
            return ResponseVO.buildSuccess("添加优惠券成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

}
