package com.example.cinema.vo;

/**
 * @author fjj
 * @date 2019/4/11 3:22 PM
 */
public class UserVO {
	
    private Integer id;
    private String username;
    private String password;
    private Integer level;
    private double ticketConsumption;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public double getTicketConsumption() {
		return ticketConsumption;
	}

	public void setTicketConsumption(double ticketConsumption) {
		this.ticketConsumption = ticketConsumption;
	}
}
