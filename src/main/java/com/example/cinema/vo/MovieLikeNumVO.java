package com.example.cinema.vo;

/**
 * Created by AzureXH on 2019/6/17
 */
public class MovieLikeNumVO {

    /**
     * 电影id
     */
    private Integer id;

    /**
     * 电影名称
     */
    private String name;

    /**
     * 电影想看人数
     */
    private Integer likeNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }
}
