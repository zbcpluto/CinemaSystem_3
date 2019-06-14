package com.example.cinema.po;

import java.sql.Timestamp;

public class VIPCharge {

    private int userId;

    private int id;

    private int vipServiceId;

    private int chargeAmount;

    private Timestamp chargeTime;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public int getVipServiceId() {
        return vipServiceId;
    }

    public int getChargeAmount() {
        return chargeAmount;
    }

    public Timestamp getChargeTime() {
        return chargeTime;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVipServiceId(int vipServiceId) {
        this.vipServiceId = vipServiceId;
    }

    public void setChargeAmount(int chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public void setChargeTime(Timestamp chargeTime) {
        this.chargeTime = chargeTime;
    }
}
