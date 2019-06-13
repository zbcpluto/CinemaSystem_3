package com.example.cinema.vo;



/**
 * Created by liying on 2019/4/15.
 */
public class VIPInfoForm {
	
	private String name;

    private double price;
    
    private int discount_req;
    
    private int discount_res;
    

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getDiscount_req() {
		return discount_req;
	}

	public void setDiscount_req(int discount_req) {
		this.discount_req = discount_req;
	}

	public int getDiscount_res() {
		return discount_res;
	}

	public void setDiscount_res(int discount_res) {
		this.discount_res = discount_res;
	}
    
}
