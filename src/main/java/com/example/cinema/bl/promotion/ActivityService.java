package com.example.cinema.bl.promotion;

import com.example.cinema.vo.ActivityForm;
import com.example.cinema.vo.ResponseVO;

/**
 * Created by liying on 2019/4/20.
 */
public interface ActivityService {

    /**
     * 发布活动
     * @param activityForm
     * @return
     */
    ResponseVO publishActivity(ActivityForm activityForm);

    /**
     * 获取活动
     * @return
     */
    ResponseVO getActivities();

    /**
     * 根据电影获取活动
     * @param movieId
     * @return
     */
	ResponseVO getActivitiesByMovieId(int movieId);

}
