package com.example.cinema.vo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.example.cinema.po.ScheduleItem;

/**
 * Created by liying on 2019/4/16.
 */
public class TicketVO {

    private int userId;
    
    private int scheduleId;
    
    private int couponId;
    
    private String posterUrl;
    
    private String movieName;
    
    private String hallName;
    
    private Date startTime;
    
    private Date endTime;
    
    private List<SeatForm> seats;
    
    private double singleFare;
    
    private double discountAmount;

    private String state;

    private Timestamp time;
    
    
    public TicketVO(int userId, int scheduleId, String posterUrl, ScheduleItem scheduleItem) {
    	this.userId = userId;
    	this.scheduleId = scheduleId;
    	this.posterUrl = posterUrl;
    	this.movieName = scheduleItem.getMovieName();
    	this.hallName = scheduleItem.getHallName();
    	this.startTime = scheduleItem.getStartTime();
    	this.endTime = scheduleItem.getEndTime();
    	this.singleFare = scheduleItem.getFare();
    }

    public void addSeat(int columnIndex, int rowIndex) {
    	seats.add(new SeatForm(columnIndex, rowIndex));
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<SeatForm> getSeats() {
		return seats;
	}

	public void setSeats(List<SeatForm> seats) {
		this.seats = seats;
	}

	public double getSingleFare() {
		return singleFare;
	}

	public void setSingleFare(double singleFare) {
		this.singleFare = singleFare;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
    
}
