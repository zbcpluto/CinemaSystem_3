package com.example.cinema.vo;

import java.util.Date;

/**
 * 获得退票手续费折算比时传递的参数
 * @author zbc
 *
 */
public class RefundRatioForm {

	private int isVip; //0: 不是，1：是会员
	
	private Date startTime;  //放映时间

	
	public int getIsVip() {
		return isVip;
	}

	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
