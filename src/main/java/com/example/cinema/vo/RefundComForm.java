package com.example.cinema.vo;

import java.util.List;

/**
 * 封装了完成退票需要的信息
 * @author zbc
 *
 */
public class RefundComForm {
	
	private double amount;
	
	private List<Integer> ticketIds;
	
	private List<Integer> couponIds;
	
	private int isVip;
	
	private int userId;
	

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public List<Integer> getTicketIds() {
		return ticketIds;
	}

	public void setTicketIds(List<Integer> ticketIds) {
		this.ticketIds = ticketIds;
	}
	
	public List<Integer> getCouponIds() {
		return couponIds;
	}

	public void setCouponIds(List<Integer> couponIds) {
		this.couponIds = couponIds;
	}

	public int getIsVip() {
		return isVip;
	}

	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}