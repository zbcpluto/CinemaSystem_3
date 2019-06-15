package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService, VIPServiceForBl {
    @Autowired
    VIPCardMapper vipCardMapper;

    @Override
    public ResponseVO addVIPCard(int userId, int serviceId) {
        VIPCardForm card = new VIPCardForm();
        card.setUserId(userId);
        card.setServiceId(serviceId);
        try {
            vipCardMapper.insertOneCard(card);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVIPInfo() {
    	try {
            return ResponseVO.buildSuccess(vipCardMapper.selectVIPInfo().stream().map(VIPInfo::getVO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO charge(VIPChargeForm chargeForm) {
    	try {
    		VIPCard card = vipCardMapper.selectCardById(chargeForm.getVipId());
            VIPInfo info = vipCardMapper.selectServiceById(card.getServiceId());
            
            VIPChargeRecordForm vrf = new VIPChargeRecordForm();
            vrf.setUserId(card.getUserId());
            vrf.setCardId(card.getId());
            vrf.setChargeAmount(chargeForm.getAmount());
            if(chargeForm.getAmount() >= info.getDiscount_req()) {
            	vrf.setDiscountAmount(info.getDiscount_res());
            }
            
            vipCardMapper.insertOneRecord(vrf);
            vipCardMapper.updateCardBalance(chargeForm.getVipId(), card.getBalance()+chargeForm.getAmount()+vrf.getDiscountAmount());
            return ResponseVO.buildSuccess();
    	} catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

	@Override
	public VIPCard selectCardByUserId(int userId) {
		try {
            return vipCardMapper.selectCardByUserId(userId);
        } catch (Exception e) {
            return null;
        }
	}


	@Override
	public ResponseVO publishVipcard(VIPInfoForm vipInfoForm) {
		try {
			vipCardMapper.insertOneVIPInfo(vipInfoForm);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}

	@Override
	public ResponseVO updateVipcard(VIPInfoForm vipInfoForm) {
		try {
			vipCardMapper.updateOneVIPInfo(vipInfoForm);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}

	@Override
    public ResponseVO getChargeHistory(int userId){
        try {
            return ResponseVO.buildSuccess(vipCardMapper.getRecordsByUserId(userId));
        } catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("查看充值记录失败");
        }
    }

	@Override
	public ResponseVO getVIPInfoById(int id) {
		try {
            VIPInfo info = vipCardMapper.selectServiceById(id);
            if(info == null){
                return ResponseVO.buildFailure("会员卡不存在");
            }
            return ResponseVO.buildSuccess(info);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}
}
