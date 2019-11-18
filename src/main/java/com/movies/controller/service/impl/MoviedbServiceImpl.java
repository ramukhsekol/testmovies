package com.movies.controller.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.MoviedbService;

@Service
public class MoviedbServiceImpl implements MoviedbService {

	@Override
	public JSONArray getMoviesByCategoryAndSearch(String category, String search, String pageIndex) throws UnirestException, UnsupportedEncodingException {
		String url = "";
		if(StringUtils.hasText(search)) {
			url = "https://api.themoviedb.org/3/search/"+category+"?api_key=663337055530cc77a3aa1d26fec365d5&query="+URLEncoder.encode(search, "UTF-8")+"&page=" + pageIndex;
		} else {
			url = "https://api.themoviedb.org/3/movie/"+category+"?api_key=663337055530cc77a3aa1d26fec365d5&page=" + pageIndex;
		}
		
		HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
		JSONArray jsonArray = jsonResponse.getBody().getObject().getJSONArray("results");
		return jsonArray;
	}

	@Override
	public HttpResponse<String> getMovieByMovieId(String movieId) throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=663337055530cc77a3aa1d26fec365d5").asString();
		return response;
	}

	@Override
	public JSONArray getTrailersByMovieId(String movieId) throws UnirestException {
		HttpResponse<JsonNode> jsonResponse = Unirest.get("https://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=663337055530cc77a3aa1d26fec365d5").asJson();
		JSONArray movietrailers = jsonResponse.getBody().getObject().getJSONArray("results");
		return movietrailers;
	}

	@Override
	public JSONArray getMoviesByLanguage(String pageIndex, String language) throws UnirestException {
	String url = "https://api.themoviedb.org/3/discover/movie?api_key=663337055530cc77a3aa1d26fec365d5&page=" + pageIndex + "&with_original_language=" + language;
	HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
	JSONArray jsonArray = jsonResponse.getBody().getObject().getJSONArray("results");
	return jsonArray;
	}

}
