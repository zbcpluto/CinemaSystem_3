package com.example.cinema.po;

import java.sql.Timestamp;

public class VIPChargeRecord {

	private int id;

	private int userId;

    private int cardId;

    private double chargeAmount;

    private double discountAmount;

    private Timestamp chargeTime;
    

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
	
	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Timestamp getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Timestamp chargeTime) {
		this.chargeTime = chargeTime;
	}

}
