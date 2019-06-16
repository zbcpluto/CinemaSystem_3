package com.example.cinema.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.cinema.po.ScheduleItem;

/**
 * Created by liying on 2019/4/16.
 */
public class TicketForm2 {

    /**
     * 用户id
     */
    private int userId;
    
    /**
     * 排片id
     */
    private int scheduleId;
    
    /**
     * 电影海报
     */
    private String posterUrl;
    
    /**
     * 电影名
     */
    private String movieName;
    
    /**
     * 影厅名称
     */
    private String hallName;
    
    /**
     * 开始放映时间
     */
    private Date startTime;
    /**
     * 结束放映时间
     */
    private Date endTime;
    
    /**
     * 座位
     */
    private List<SeatForm> seats;
    
    private double singlePrice;
    
    /**
     * 订单状态：
     * 0：未完成 1：已完成 2:已失效
     */
    private int state;
    
    public TicketForm2() {
    	seats = new ArrayList<SeatForm>();
    }
    
    public TicketForm2(int userId, int scheduleId, String posterUrl, ScheduleItem scheduleItem) {
		this.userId = userId;
		this.scheduleId = scheduleId;
		this.posterUrl = posterUrl;
		this.movieName = scheduleItem.getMovieName();
		this.hallName = scheduleItem.getHallName();
		this.startTime = scheduleItem.getStartTime();
		this.endTime = scheduleItem.getEndTime();
		this.singlePrice = scheduleItem.getFare();
		seats = new ArrayList<SeatForm>();
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

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String postUrl) {
		this.posterUrl = postUrl;
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

	public double getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(double singlePrice) {
		this.singlePrice = singlePrice;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
    
    
}
