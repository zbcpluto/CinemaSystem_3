package com.example.cinema.po;

/**
 * @author huwen
 * @date 2019/3/23
 */
public class User {
	
    private Integer id;
    private String username;
    private String password;
    private Integer level;
    private double ticketConsumption;
    
    public User(){}

    public User(String username, String password, Integer level) {
        this.username = username;
        this.password = password;
        this.level = level;
    }

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
