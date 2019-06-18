package com.example.cinema.blImpl.sales;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.movie.MovieServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.blImpl.promotion.CouponServiceForBl;
import com.example.cinema.blImpl.promotion.VIPServiceForBl;
import com.example.cinema.blImpl.user.AccountServiceForBl;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;

/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    MovieServiceForBl movieService;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    AccountServiceForBl accountServiceForBl;
    @Autowired
    HallServiceForBl hallService;
    @Autowired
    CouponServiceForBl couponService;
    @Autowired
    VIPServiceForBl vipService;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
    	try {
            int userId = ticketForm.getUserId();
            int scheduleId = ticketForm.getScheduleId();
            List<SeatForm> seats = ticketForm.getSeats();
            List<Integer> tickeIdList = new ArrayList<>();
            
            Timestamp now = new Timestamp(new Date().getTime());
            for(SeatForm s: seats) {
                Ticket ticket = new Ticket();
                ticket.setUserId(userId) ;
                ticket.setScheduleId(scheduleId);
                ticket.setColumnIndex(s.getColumnIndex());
                ticket.setRowIndex(s.getRowIndex());
                ticket.setTime(now);
                ticketMapper.insertTicket(ticket);
                tickeIdList.add(ticket.getId());
            }  //对于每一张ticket都在数据库中添加一个ticket对象

            return ResponseVO.buildSuccess(tickeIdList);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("选座失败！");
        }
    }

    @Override
    @Transactional
    public ResponseVO completeTicket(TicketBuyForm ticketBuyForm) {
    	try {
    		int couponId = ticketBuyForm.getCouponId();
    		List<Integer> ticketIdList = ticketBuyForm.getTicketId();
    		List<Ticket> tickets = new ArrayList<Ticket>();
    		List<Integer> couponIdToget = ticketBuyForm.getCouponIdToget();
    		
            for(int id: ticketIdList) {
                tickets.add(ticketMapper.selectTicketById(id));
            }
            int userId = tickets.get(0).getUserId();
            
    		//更新ticket状态
            for (Ticket t: tickets) {
                t.setState(1);
                ticketMapper.updateTicketState(t.getId(), 1);
                ticketMapper.updatePaymentMode(t.getId(), 0);  //改变ticket的购买方式为银行卡支付（0）
                if(couponId != 0) {
                    ticketMapper.updateTicketCoupon(t.getId(), couponId);
                } //更新ticket使用的couponId
            }
            
            //更新优惠券
            if(couponId != 0) {
            	couponService.deleteCouponUser(couponId, userId);
            }
            for(int id: couponIdToget) {
                couponService.addCouponUser(id, userId);
            }

            return ResponseVO.buildSuccess();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule = scheduleService.getScheduleItemById(scheduleId);
            Hall hall = hallService.getHallById(schedule.getHallId());
            int[][] seats = new int[hall.getRow()][hall.getColumn()];
            tickets.stream().forEach(ticket -> {
                seats[ticket.getRowIndex()][ticket.getColumnIndex()]=1;
            });
            ScheduleWithSeatVO scheduleWithSeatVO = new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
    	try {
    		List<Ticket> tickets = ticketMapper.selectTicketByUser(userId);
    		Map<Long, TicketVO> map = new HashMap<Long, TicketVO>();
    		tickets.stream().forEach(ticket -> {
    			long time = ticket.getTime().getTime();
    			if(!map.containsKey(time)) {
    				int scheduleId = ticket.getScheduleId();
    				ScheduleItem scheduleItem = scheduleService.getScheduleItemById(scheduleId);
    				Movie movie = movieService.getMovieById(scheduleItem.getMovieId());
    				String posterUrl = movie.getPosterUrl();
    				TicketVO tv = new TicketVO(userId, scheduleId, posterUrl, scheduleItem);
    				tv.addSeat(ticket.getColumnIndex(), ticket.getRowIndex());
    				tv.addId(ticket.getId());
    				tv.setState(ticket.getState());
    				
    				int couponId = ticket.getCouponId();
    				tv.setCouponId(couponId);
    				if(couponId == 0) {
    					tv.setCouponDes("无");
    				}
    				else {
    					Coupon coupon = couponService.getCouponById(tv.getCouponId());
    					StringBuilder sb = new StringBuilder();
    					sb.append(coupon.getName()).append("(满 ");
    					sb.append(coupon.getTargetAmount()).append(" 减 ");
    					sb.append(coupon.getDiscountAmount()).append(")");
        				tv.setCouponDes(sb.toString());
    				}
    				tv.setTime(ticket.getTime());
    				map.put(time, tv);
    			}
    			else {
    				TicketVO tv = map.get(time);
    				tv.addSeat(ticket.getColumnIndex(), ticket.getRowIndex());
    				tv.addId(ticket.getId());
    			}
    		});
    		
    		List<TicketVO> tvs = new ArrayList<TicketVO>();
    		for(TicketVO tv: map.values()) {
    			tvs.add(tv);
    		}
    		Collections.sort(tvs);
    		Collections.reverse(tvs);
    		return ResponseVO.buildSuccess(tvs);
        }
    	catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    @Transactional
    public ResponseVO completeByVIPCard(TicketBuyForm ticketBuyForm) {
    	try {
    		int couponId = ticketBuyForm.getCouponId();
    		double total = ticketBuyForm.getTotal();
    		List<Integer> ticketIdList = ticketBuyForm.getTicketId();
    		List<Integer> couponIdToget = ticketBuyForm.getCouponIdToget();
    		
    		List<Ticket> tickets = new ArrayList<Ticket>();
            for(int id: ticketIdList) {
                tickets.add(ticketMapper.selectTicketById(id));
            }
            int userId = tickets.get(0).getUserId();
    		//会员卡扣费
            ticketMapper.VIPPay(userId, total);
    		//更新ticket状态
            for (Ticket t: tickets) {
                t.setState(1);
                ticketMapper.updateTicketState(t.getId(), 1);
                ticketMapper.updatePaymentMode(t.getId(), 1);  //改变ticket的购买方式为会员卡支付（1）
                if(couponId != 0) {
                    ticketMapper.updateTicketCoupon(t.getId(), couponId);
                } //更新ticket使用的couponId
            }
            
            //更新优惠券
            if(couponId != 0) {
            	couponService.deleteCouponUser(couponId, userId);
            }
            for(int id: couponIdToget) {
                couponService.addCouponUser(id, userId);
            }

            return ResponseVO.buildSuccess();
        }
    	catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO cancelTicket(List<Integer> id) {
    	 try {
             for (int i: id) {
                 ticketMapper.updateTicketState(i, 2);
             }
             return ResponseVO.buildSuccess();
         }
    	 catch (Exception e) {
             e.printStackTrace();
             return ResponseVO.buildFailure("失败!");
         }
    }

    @Override
    public ResponseVO getTicketRefund(List<Integer> ticketId){
        try {
            List<Ticket> tickets=new ArrayList<Ticket>();
            for(int i:ticketId){
                tickets.add(ticketMapper.selectTicketById(i));
            }
            int userId=tickets.get(0).getUserId();  //得到用户ID
            int movieId=tickets.get(0).getScheduleId(); // 得到电影ID
            int couponId=tickets.get(0).getCouponId(); //得到ticket使用的优惠券ID
            Timestamp timestamp=tickets.get(0).getTime(); //得到电影购买的时间
            int paymentMode=tickets.get(0).getPaymentMode(); //得到购买的方式（0: 银行卡; 1: 会员卡支付f）
            int scheduleId=tickets.get(0).getScheduleId();
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId); // 得到电影的排片信息（主要是取得排片对应的票价）
            double penalty=0;//这里是假数据，之后的过程中要从数据库中获得penalty的比例
            int penaltyMode=isVip(userId); // 得到罚金的模式（vip是1，非vip是0）
            List<RefundStategy> refundStrategies=ticketMapper.getRefundStrategies(penaltyMode); // 得到退票的罚金策略（根据罚金模式不同而不同）
            Date currentTime= new Date(); //得到现在的时间
            Date onTime=schedule.getStartTime();  //得到电影的开场时间
            double beforeOn=(double)(currentTime.getTime()-onTime.getTime())/(double)(1000*60); //计算现在离开场的时间还有多久
            for(RefundStategy rs:refundStrategies){
                double startTime=Double.parseDouble(rs.getStartTime().split(":")[0])*60+
                        Double.parseDouble(rs.getStartTime().split(":")[1]);
                double endTime=Double.parseDouble(rs.getEndTime().split(":")[0])*60+
                        Double.parseDouble(rs.getEndTime().split(":")[1]);
                double falseTime=Double.parseDouble(rs.getFalseTime().split(":")[0])*60+
                        Double.parseDouble(rs.getFalseTime().split(":")[1]);

                if(beforeOn>=falseTime){   //判断是否已经超过可退票的最低时限
                    if(beforeOn> startTime && beforeOn<=endTime){
                        penalty=rs.getPenalty();
                        break;             //判断退票时间段处于可退票的哪个时间段内并且得到罚金比例
                    }
                }
                else{
                    return ResponseVO.buildFailure("已经超过可退票的最低时限，退票失败");
                }
            }
            double fare=0;
            double discountByCoupon=0;
            for(Ticket t:tickets){
                fare=fare+schedule.getFare(); //得到票价
                ticketMapper.updateTicketState(t.getId(),2);//删除购买记录，本质上是把电影票的支付状态改成已失效（2）
            }
            discountByCoupon= couponService.getCouponById(couponId).getDiscountAmount();
            fare=fare-discountByCoupon;
            double refund=(1-penalty)*fare;  //计算要退的金额
            switch(paymentMode){
                case 1:
                {
                    ticketMapper.VIPRefund(userId,refund);
                    break;    //退款
                }
            }

            accountServiceForBl.updateTicketConsumption(userId,0-refund); // user表中更新购票消费
            List<ActivityMovie> activityMovies = activityService.selectActivityByTimeAndMovie(timestamp, movieId);
            List<Coupon> couponsToCatch = new ArrayList<Coupon>();
            for(Activity i:activities){
                if(!couponService.existCouponUser(i.getCoupon().getId(), userId)){
                    couponsToCatch.add(i.getCoupon());
                }
            } //构造根据活动需要拿回的优惠券列表

            if(couponsToCatch.size()!=0){
                for(Coupon coupon:couponsToCatch){
                    couponService.deleteCouponUser(coupon.getId(),userId);
                }
            }//删除已经给出的用户优惠券（在coupon_user表中删除）
            return ResponseVO.buildSuccess("退票成功");

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("退票失败");
        }
    }

    private int isVip(int userId){
        if(ticketMapper.isVip(userId).size()!=0){
            return 1;
        }
        else{
            return 0;
        }
    }


}
