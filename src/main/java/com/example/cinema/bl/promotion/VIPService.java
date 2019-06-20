package com.example.cinema.bl.promotion;

import com.example.cinema.vo.VIPChargeForm;
import com.example.cinema.vo.VIPInfoForm;
import com.example.cinema.vo.ResponseVO;



/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    /**
     * 给某个用户分配一张会员卡
     * @param userId
     * @param serviceId
     * @return
     */
    ResponseVO addVIPCard(int userId, int serviceId);

    /**
     * 根据会员卡id得到会员卡
     * @param id
     * @return
     */
    ResponseVO getCardById(int id);

    /**
     * 得到会员卡全部信息
     * @return
     */
    ResponseVO getVIPInfo();

    /**
     * 会员卡消费
     * @param vipChargeForm
     * @return
     */
    ResponseVO charge(VIPChargeForm vipChargeForm);

    /**
     * 根据用户id获取用户的会员卡
     * @param userId
     * @return
     */
    ResponseVO getCardByUserId(int userId);

    /**
     * 发布会员卡
     * @param vipInfoForm
     * @return
     */
    ResponseVO publishVipcard(VIPInfoForm vipInfoForm);

    /**
     * 修改会员卡
     * @param vipInfoForm
     * @return
     */
	ResponseVO updateVipcard(VIPInfoForm vipInfoForm);

    /**
     * 根据会员卡id获取会员卡信息
     * @param id
     * @return
     */
	ResponseVO getVIPInfoById(int id);

    /**
     * 得到对应用户的会员卡消费记录
     * @param userId
     * @return
     */
	ResponseVO getChargeHistory(int userId);
    
}
