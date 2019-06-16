package com.example.cinema.blImpl.user;

import com.example.cinema.vo.ResponseVO;

public interface AccountServiceForBl {

    ResponseVO updateTicketConsumption(int userId, double ticketConsumption);
}
