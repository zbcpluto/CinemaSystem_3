package com.example.cinema.blImpl.sales;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.cinema.vo.RefundComForm;
import com.example.cinema.vo.ScheduleWithSeatVO;
import com.example.cinema.vo.SeatForm;
import com.example.cinema.vo.TicketBuyForm;
import com.example.cinema.vo.TicketForm;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TicketServiceImplTest {

	@Autowired
    private TicketServiceImpl ticketService;
	
	/**
	 * 测试锁座
	 */
	@Test
	public void test1() {
		TicketForm tf = getTicketForm();
		
		ScheduleWithSeatVO swsv = (ScheduleWithSeatVO) ticketService.getBySchedule(73).getContent();
		Assert.assertFalse(swsv.existSeat(0, 2));
		ticketService.addTicket(tf).getContent();
		swsv = (ScheduleWithSeatVO) ticketService.getBySchedule(73).getContent();
		Assert.assertTrue(swsv.existSeat(0, 2));
	}
	
	/**
	 * 测试购票
	 */
	@Test
	public void test2() {
		TicketBuyForm tbf = getTicketBuyForm();
		Assert.assertTrue(ticketService.completeTicket(tbf).getSuccess());
	}
	
	/**
	 * 测试会员卡购票
	 */
	@Test
	public void test3() {
		TicketBuyForm tbf = getTicketBuyForm();
		
		Assert.assertTrue(ticketService.completeByVIPCard(tbf).getSuccess());
	}
	
	/**
	 * 测试退票
	 * @return
	 */
	@Test
	public void test4() {
		RefundComForm rcf = getRefundComForm();
		
		Assert.assertTrue(ticketService.completeRefund(rcf).getSuccess());
	}
	
	/**
	 * 测试退票2
	 * @return
	 */
	@Test
	public void test5() {
		RefundComForm rcf = getRefundComForm();
		rcf.setIsVip(0);
		
		Assert.assertTrue(ticketService.completeRefund(rcf).getSuccess());
	}
	
	private TicketForm getTicketForm() {
		TicketForm tf = new TicketForm();
		tf.setUserId(3);
		tf.setScheduleId(73);
		List<SeatForm> seats = new ArrayList<SeatForm>();
		SeatForm sf = new SeatForm();
		sf.setColumnIndex(0);
		sf.setRowIndex(2);
		seats.add(sf);
		tf.setSeats(seats);
		
		return tf;
	}
	
	private TicketBuyForm getTicketBuyForm() {
		TicketForm tf = getTicketForm();
		@SuppressWarnings("unchecked")
		List<Integer> ticketIdList = (List<Integer>) ticketService.addTicket(tf).getContent();
		
		TicketBuyForm tbf = new TicketBuyForm();
		tbf.setMovieId(10);
		tbf.setTicketId(ticketIdList);
		tbf.setCouponId(10);
		List<Integer> couponIdToget = new ArrayList<Integer>();
		couponIdToget.add(28);
		tbf.setCouponIdToget(couponIdToget);
		tbf.setTotal(100);
		
		return tbf;
	}
	
	private RefundComForm getRefundComForm() {
		RefundComForm rcf = new RefundComForm();
		TicketForm tf = getTicketForm();
		@SuppressWarnings("unchecked")
		List<Integer> ticketIdList = (List<Integer>) ticketService.addTicket(tf).getContent();
		
		rcf.setUserId(3);
		rcf.setAmount(95);
		rcf.setIsVip(1);
		rcf.setCouponIds(new ArrayList<Integer>());
		rcf.setTicketIds(ticketIdList);
		
		return rcf;
	}
}
