package com.example.cinema.data.promotion;

import com.example.cinema.po.*;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.VIPChargeRecordForm;
import com.example.cinema.vo.VIPInfoForm;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liying on 2019/4/14.
 */
@Mapper
public interface VIPCardMapper {

    void insertOneCard(VIPCardForm card);

    VIPCard selectCardById(int id);

    void updateCardBalance(@Param("id") int id, @Param("balance") double balance);

    VIPCard selectCardByUserId(int userId);

	List<VIPInfo> selectVIPInfo();

	void insertOneVIPInfo(VIPInfoForm vipInfoForm);

	void updateOneVIPInfo(VIPInfoForm vipInfoForm); 

	List<VIPChargeRecord> getRecordsByUserId(int userId);

	VIPInfo selectServiceById(int id);

	void insertOneRecord(VIPChargeRecordForm vrf);
}
