package com.example.cinema.po;

import javax.jws.soap.SOAPBinding;

/**
 * @author huwen
 * @date 2019/3/23
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer level;
    public User(){

    }

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
}
