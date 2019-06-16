package com.example.cinema.data.user;

import com.example.cinema.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Mapper
public interface AccountMapper {

    /**
     * 创建一个新的账号
     * @param username
     * @param password
     * @return
     */
	int createNewAccount(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名查找账号
     * @param username
     * @return
     */
	User getAccountByName(@Param("username") String username);

	User getAccountById(@Param("userId") int userId);

	void updateTicketConsumption(@Param("userId")int userId, @Param("ticketConsumption") double ticketConsumption);

	void managerAssign(User user);

}
