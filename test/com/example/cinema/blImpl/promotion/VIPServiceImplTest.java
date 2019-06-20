package com.example.cinema.blImpl.promotion;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPInfo;
import com.example.cinema.vo.VIPChargeForm;
import com.example.cinema.vo.VIPInfoForm;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VIPServiceImplTest {
	
	@Autowired
    private VIPServiceImpl service;

	@Test
	public void testGetVIPById() {
		service.getCardById(15);
	}
	
	@Test
	public void testGetVIPInfo() {
		service.getVIPInfo();
	}
	
	@Test
	public void testCharge1() {
		VIPCard vc = (VIPCard) service.getCardById(1).getContent();
		double balance = vc.getBalance();
		VIPChargeForm vcf = new VIPChargeForm();
		vcf.setVipId(1);
		vcf.setAmount(20);
		service.charge(vcf);
		vc = (VIPCard) service.getCardById(1).getContent();
		Assert.assertEquals("equals", vc.getBalance()-balance, vcf.getAmount(), 0);
	}
	
	@Test
	public void testCharge2() {
		VIPCard vc = (VIPCard) service.getCardById(1).getContent();
		double balance = vc.getBalance();
		VIPChargeForm vcf = new VIPChargeForm();
		vcf.setVipId(1);
		vcf.setAmount(200);
		service.charge(vcf);
		vc = (VIPCard) service.getCardById(1).getContent();
		VIPInfo vi = (VIPInfo) service.getVIPInfoById(vc.getId()).getContent();
		Assert.assertEquals("equals", vc.getBalance()-balance, vcf.getAmount()+vi.getDiscount_res(), 0);
	}
	
	@Test
	public void testGetCardByUserId() {
		VIPCard vc = (VIPCard) service.getCardById(1).getContent();
		double balance = vc.getBalance();
		VIPChargeForm vcf = new VIPChargeForm();
		vcf.setVipId(1);
		vcf.setAmount(200);
		service.charge(vcf);
		vc = (VIPCard) service.getCardById(1).getContent();
		VIPInfo vi = (VIPInfo) service.getVIPInfoById(vc.getId()).getContent();
		Assert.assertEquals("equals", vc.getBalance()-balance, vcf.getAmount()+vi.getDiscount_res(), 0);
	}
	
	@Test
	public void testUpdateVipcard() {
		VIPInfoForm vf = new VIPInfoForm();
		vf.setId(1);
		vf.setName("新的名字");
		service.updateVipcard(vf);
		VIPInfo vi = (VIPInfo) service.getVIPInfoById(1).getContent();
		Assert.assertEquals(vi.getName(), vf.getName());
	}

}
