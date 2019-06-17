package com.example.cinema.po;

/**
 * Created by AzureXH on 2019/6/17
 */
public class MovieLikeNum {

    /**
     * 电影Id
     */
    private Integer movieId;

    /**
     * 电影想看人数
     */
    private Integer likeNum;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }
}
