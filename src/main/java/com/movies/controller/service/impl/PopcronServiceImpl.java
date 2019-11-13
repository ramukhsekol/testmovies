package com.movies.controller.service.impl;

import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.PopcronService;
import com.movies.util.IpAddressUtil;

@Service
public class PopcronServiceImpl implements PopcronService {

	@Override
	public HttpResponse<String> getmovierooms() throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://tv-v2.api-fetch.website/movies").asString();
		return response;
	}

	@Override
	public HttpResponse<String> getMoviesByIndex(String movieIndex) throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://tv-v2.api-fetch.website/movies/"+movieIndex).asString();
		return response;
	}

	@Override
	public HttpResponse<String> getMoviesById(String movieId) throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://tv-v2.api-fetch.website/movie/"+movieId).asString();
		return response;
	}

	@Override
	public HttpResponse<String> getMovieTricket(String movieId) throws UnirestException, UnknownHostException {
		String ipAddress = IpAddressUtil.getPublicIpAddress();
		HttpResponse<String> response = Unirest.get("https://videospider.in/getticket.php?key=5HbImlTRhrrI7aEO&secret_key=s51qflw236t2ddlcbom9d0hfe800tp&video_id="+movieId+"&ip="+ipAddress).asString();
		return response;
	}

}
