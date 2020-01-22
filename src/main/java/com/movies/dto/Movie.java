package com.movies.dto;

import java.util.List;

public class Movie {
	private Long id;
	private String movieName;
	private String movieInfo;
	private String movieDescription;
	private String movieImage;
	private Integer movieYear;
	private List<MovieLinks> movieLinks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getMovieInfo() {
		return movieInfo;
	}

	public void setMovieInfo(String movieInfo) {
		this.movieInfo = movieInfo;
	}

	public String getMovieDescription() {
		return movieDescription;
	}

	public void setMovieDescription(String movieDescription) {
		this.movieDescription = movieDescription;
	}

	public String getMovieImage() {
		return movieImage;
	}

	public void setMovieImage(String movieImage) {
		this.movieImage = movieImage;
	}

	public Integer getMovieYear() {
		return movieYear;
	}

	public void setMovieYear(Integer movieYear) {
		this.movieYear = movieYear;
	}

	public List<MovieLinks> getMovieLinks() {
		return movieLinks;
	}

	public void setMovieLinks(List<MovieLinks> movieLinks) {
		this.movieLinks = movieLinks;
	}

}
