package com.example.cinema.bl.promotion;

import com.example.cinema.vo.VIPChargeForm;
import com.example.cinema.vo.VIPInfoForm;
import com.example.cinema.vo.ResponseVO;



/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    ResponseVO addVIPCard(int userId, int serviceId);

    ResponseVO getCardById(int id);

    ResponseVO getVIPInfo();

    ResponseVO charge(VIPChargeForm vipChargeForm);

    ResponseVO getCardByUserId(int userId);

    ResponseVO publishVipcard(VIPInfoForm vipInfoForm);

	ResponseVO updateVipcard(VIPInfoForm vipInfoForm);
	
	ResponseVO getVIPInfoById(int id);

	ResponseVO getChargeHistory(int userId);
    
}
