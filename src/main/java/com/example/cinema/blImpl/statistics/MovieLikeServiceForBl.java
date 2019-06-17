package com.example.cinema.blImpl.statistics;

import com.example.cinema.po.MovieLikeNum;

import java.util.List;

/**
 * Created by AzureXH on 2019/6/17
 */
public interface MovieLikeServiceForBl {

    /**
     * 获取想看人数  降序排列
     * @return
     */
    List<MovieLikeNum> getMovieLikeNumByLikeDesc();
}
