package com.example.cinema.vo;

/**
 * Created by liying on 2019/4/14.
 */
public class VIPCardForm {
    /**
     * 用户id
     */
    private int userId;
    
    /**
     * 会员卡种类ID
     */
    private int serviceId;

    
    public VIPCardForm() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSerivceId(){
        return this.serviceId;
    }

    public void setServiceId(int serviceId){
        this.serviceId = serviceId;
    }

}
