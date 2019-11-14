package com.movies.controller.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.PopcronService;

@Service
public class PopcronServiceImpl implements PopcronService {
	
	@Override
	public HttpResponse<String> getMoviesById(String pageIndex, String genreType) throws UnirestException, UnsupportedEncodingException {
		String url = "https://tv-v2.api-fetch.website/movies/" + pageIndex;
		if(StringUtils.hasText(genreType)) {
			url = url + "?genre=" +  URLEncoder.encode(genreType, "UTF-8");
		}
		HttpResponse<String> response = Unirest.get(url).asString();
		return response;
	}

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

	

	

}
