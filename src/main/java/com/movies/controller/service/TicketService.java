package com.movies.controller.service;

import java.net.UnknownHostException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface TicketService {
	
	public HttpResponse<String> getMovieTricket(String movieId, String ipAddress) throws UnirestException, UnknownHostException;

}
