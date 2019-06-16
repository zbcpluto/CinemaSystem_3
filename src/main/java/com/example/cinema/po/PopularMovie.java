package com.example.cinema.po;

public class PopularMovie {
    /**
     * 电影id
     */
    private Integer movieId;
    /**
     * 观众票数
     */
    private Integer times;

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
}
