package com.example.cinema.vo;

import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
public class TicketBuyForm {
	
	private int movieId;
	
	private List<Integer> ticketId;
	
	private int couponId;
	
	private double total;
	
	private List<Integer> couponIdToget;

	
	public int getMovieId() {
		return movieId;
	}
	
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public List<Integer> getTicketId() {
		return ticketId;
	}

	public void setTicketId(List<Integer> ticketId) {
		this.ticketId = ticketId;
	}

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<Integer> getCouponIdToget() {
		return couponIdToget;
	}

	public void setCouponIdToget(List<Integer> couponIdToget) {
		this.couponIdToget = couponIdToget;
	}
	
}
