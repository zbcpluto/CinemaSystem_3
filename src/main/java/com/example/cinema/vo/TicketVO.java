package com.example.cinema.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.cinema.po.ScheduleItem;

/**
 * Created by liying on 2019/4/16.
 */
public class TicketVO implements Comparable<TicketVO> {

    private int userId;
    
    private int movieId;
    
    private int scheduleId;
    
    private int couponId;
    
    private String posterUrl;
    
    private String movieName;
    
    private String hallName;
    
    private Date startTime;
    
    private Date endTime;
    
    private List<SeatForm> seats;
    
    private double singleFare;
    
    private String couponDes;

    private int state;

    private Timestamp time;
    
    private List<Integer> idList;
    
    
    public TicketVO(int userId, int scheduleId, String posterUrl, ScheduleItem scheduleItem) {
    	this.userId = userId;
    	this.scheduleId = scheduleId;
    	this.posterUrl = posterUrl;
    	this.movieId = scheduleItem.getMovieId();
    	this.movieName = scheduleItem.getMovieName();
    	this.hallName = scheduleItem.getHallName();
    	this.startTime = scheduleItem.getStartTime();
    	this.endTime = scheduleItem.getEndTime();
    	this.singleFare = scheduleItem.getFare();
    	this.seats = new ArrayList<>();
    	this.idList = new ArrayList<>();
    }

    public void addSeat(int columnIndex, int rowIndex) {
    	seats.add(new SeatForm(columnIndex, rowIndex));
    }
    
    public void addId(int id) {
    	idList.add(id);
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
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

	public String getCouponDes() {
		return couponDes;
	}

	public void setCouponDes(String couponDes) {
		this.couponDes = couponDes;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}

	@Override
	public int compareTo(TicketVO o) {
		return time.compareTo(o.time);
	}

}
