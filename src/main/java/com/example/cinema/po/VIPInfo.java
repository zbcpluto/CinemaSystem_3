package com.example.cinema.po;

import com.example.cinema.vo.VIPInfoVO;

public class VIPInfo {
	
	private int id;
	
	private String name;

    private double price;
    
    private int discount_req;
    
    private int discount_res;
    
    public VIPInfoVO getVO() {
    	VIPInfoVO vip = new VIPInfoVO();
    	vip.setId(id);
    	vip.setName(name);
    	vip.setPrice(price);
    	vip.setDiscount_req(discount_req);
    	vip.setDiscount_res(discount_res);
    	return vip;
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
