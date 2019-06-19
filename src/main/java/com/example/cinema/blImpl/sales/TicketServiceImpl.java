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
    		double total = ticketBuyForm.getTotal();
    		List<Integer> ticketIdList = ticketBuyForm.getTicketId();
    		List<Ticket> tickets = new ArrayList<Ticket>();
    		List<Integer> couponIdToget = ticketBuyForm.getCouponIdToget();
    		
            for(int id: ticketIdList) {
                tickets.add(ticketMapper.selectTicketById(id));
            }
            int userId = tickets.get(0).getUserId();
            ticketMapper.updateConsumption(total,userId);
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
    		ticketMapper.updateConsumption(total, userId);
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
    
    /**
     * 完成退票，具体要实现（电影票状态更改，退款至会员卡（如果有的话），收回赠送的优惠券）
     */
	@Override
	public ResponseVO completeRefund(RefundComForm rcf) {
		try {
			for(int i: rcf.getTicketIds()) {
	            ticketMapper.updateTicketState(i, 2);
	        }
	        double total = rcf.getAmount();
			int userId = rcf.getUserId();
            ticketMapper.updateConsumption(-total, userId);
			vipService.chargeCardByUser(rcf.getUserId(), rcf.getAmount());
			for(int i: rcf.getCouponIds()) {
				couponService.deleteCouponUser(i, rcf.getUserId());
			}
			return ResponseVO.buildSuccess();
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("退票失败!");
		}
	}

}
