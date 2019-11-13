package com.movies.controller.service;

import java.net.UnknownHostException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface PopcronService {

	public HttpResponse<String> getmovierooms() throws UnirestException;
	public HttpResponse<String> getMoviesByIndex(String movieIndex) throws UnirestException;
	public HttpResponse<String> getMoviesById(String movieId) throws UnirestException;
	public HttpResponse<String> getMovieTricket(String movieId) throws UnirestException, UnknownHostException;

}
