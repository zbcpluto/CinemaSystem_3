package com.example.cinema.po;

import java.sql.Timestamp;

public class VIPCharge {

    private int userId;

    private int id;

    private int vipServiceId;

    private double chargeAmount;

    private double calculatedAmount;

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

    public double getChargeAmount() {
        return chargeAmount;
    }

    public Timestamp getChargeTime() {
        return chargeTime;
    }

    public double getCalculatedAmount() {
        return calculatedAmount;
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

    public void setChargeTime(Timestamp chargeTime) {
        this.chargeTime = chargeTime;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public void setCalculatedAmount(double calculatedAmount) {
        this.calculatedAmount = calculatedAmount;
    }

    public VIPCharge(int userId,int id,int vipServiceId,double chargeAmount,double calculatedAmount,Timestamp chargeTime){
        this.userId=userId;
        this.id=id;
        this.vipServiceId=vipServiceId;
        this.chargeAmount=chargeAmount;
        this.calculatedAmount=calculatedAmount;
        this.chargeTime=chargeTime;
    }
}
