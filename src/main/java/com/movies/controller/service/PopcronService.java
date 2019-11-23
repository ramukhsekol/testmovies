package com.movies.controller.service;

import java.io.UnsupportedEncodingException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface PopcronService {

	public HttpResponse<String> getMoviesById(String pageIndex, String genreType, String keyword) throws UnirestException, UnsupportedEncodingException;
	public HttpResponse<String> getmovierooms() throws UnirestException;
	public HttpResponse<String> getMoviesByIndex(String movieIndex) throws UnirestException;
	public HttpResponse<String> getMoviesById(String movieId) throws UnirestException;
	
	

}
