package com.movies.controller.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface PopcronService {

	public HttpResponse<String> getmovierooms() throws UnirestException;

}
