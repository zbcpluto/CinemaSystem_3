package com.example.cinema.data.promotion;

import com.example.cinema.po.Coupon;
import com.example.cinema.po.CouponUser;
import com.example.cinema.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/17.
 */
@Mapper
public interface CouponMapper {

    int insertOneCoupon(Coupon coupon);

    Coupon selectCounponById(int id);

    void insertCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);

    void deleteCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);
    
    List<CouponUser> selectCouponUserByUserId(int userId);

    List<Coupon> selectCouponByUserAndAmount(@Param("userId") int userId,@Param("amount") double amount);

    List<User> selectUserByTicketConsumption(@Param("ticketConsumption")double ticketConsumption);
	
	void addCoupon(int id, int userId);
	
}
