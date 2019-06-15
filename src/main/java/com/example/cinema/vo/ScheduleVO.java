package com.example.cinema.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/12 4:05 PM
 */
public class ScheduleVO implements Comparable<ScheduleVO> {
    private Date date;
    private List<ScheduleItemVO> scheduleItemList;
    
    public ScheduleVO() {
    	scheduleItemList = new ArrayList<ScheduleItemVO>();
    }
    
    public void addScheduleItem(ScheduleItemVO siv) {
    	scheduleItemList.add(siv);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ScheduleItemVO> getScheduleItemList() {
        return scheduleItemList;
    }

    public void setScheduleItemList(List<ScheduleItemVO> scheduleItemList) {
        this.scheduleItemList = scheduleItemList;
    }

	@Override
	public int compareTo(ScheduleVO vo) {
		return date.compareTo(vo.date);
	}
}
