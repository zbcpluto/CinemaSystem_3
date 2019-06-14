package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPCharge;
import com.example.cinema.po.VIPInfo;
import com.example.cinema.vo.VIPInfoForm;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liying on 2019/4/14.
 */
@Mapper
public interface VIPCardMapper {

    int insertOneCard(VIPCard vipCard);

    VIPCard selectCardById(int id);

    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);

    VIPCard selectCardByUserId(int userId);

	List<VIPInfo> selectVIPInfo();

	void insertOneVIPInfo(VIPInfoForm vipInfoForm);

	void insertVIPCharge(VIPCharge vipCharge);
	void updateOneVIPInfo(VIPInfoForm vipInfoForm); 

	List<VIPCharge> getChargeHistory(int userId);
}
