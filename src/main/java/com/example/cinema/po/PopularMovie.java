package com.example.cinema.po;

public class PopularMovie {
    /**
     * 电影id
     */
    private Integer id;
    /**
     * 电影名称
     */
    private String name;
    /**
     * 票房次数
     */
    private Integer times;
    /**
     * 受欢迎百分比
     */
    private String popularRate;
	
    
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
	
	public Integer getTimes() {
		return times;
	}
	
	public void setTimes(Integer times) {
		this.times = times;
	}
	
	public String getPopularRate() {
		return popularRate;
	}
	
	public void setPopularRate(String popularRate) {
		this.popularRate = popularRate;
	}
    
}
