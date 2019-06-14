package com.example.cinema.bl.promotion;

import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.VIPInfoForm;
import com.example.cinema.vo.ResponseVO;



/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    ResponseVO addVIPCard(int userId, int vipServiceId);

    ResponseVO getCardById(int id);

    ResponseVO getVIPInfo();

    ResponseVO charge(VIPCardForm vipCardForm);

    ResponseVO getCardByUserId(int userId);

    ResponseVO publishVipcard(VIPInfoForm vipInfoForm);

    ResponseVO getChargeHistory(int userId);
    
	ResponseVO updateVipcard(VIPInfoForm vipInfoForm);
    
}
