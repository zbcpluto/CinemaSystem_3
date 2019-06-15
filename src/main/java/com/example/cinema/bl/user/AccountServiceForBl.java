package com.example.cinema.bl.user;

import com.example.cinema.vo.ResponseVO;

public interface AccountServiceForBl {

    ResponseVO updateTicketConsumption(int userId, double ticketConsumption);
}
