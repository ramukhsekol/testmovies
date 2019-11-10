package com.movies.controller.service.impl;

import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.PopcronService;

@Service
public class PopcronServiceImpl implements PopcronService {

	@Override
	public HttpResponse<String> getmovierooms() throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://tv-v2.api-fetch.website/movies").asString();
		return response;
	}

}
