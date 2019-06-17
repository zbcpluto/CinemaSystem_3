package com.example.cinema.po;

import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/16.
 */
public class Ticket {

    /**
     * 电影票id
     */
    private int id;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 排片id
     */
    private int scheduleId;
    /**
     * 列号
     */
    private int columnIndex;
    /**
     * 排号
     */
    private int rowIndex;

    /**
     * 订单状态：
     * 0：未完成 1：已完成 2:已失效
     */
    private int state;

    /**
     * 票的支付方式：
     * 0：银行卡 1：会员卡 -1:尚未支付
     */
    private int paymentMode;

    /**
     * 票所用的优惠券ID, 若没用到则为0
     */
    private int couponId;

    private Timestamp time;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPaymentMode(){
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode){
        this.paymentMode=paymentMode;
    }

    public int getCouponId(){
        return this.couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }
    
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
    
}
