package com.movies.controller.service;

import org.json.JSONArray;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface MoviedbService {

	JSONArray getMoviesByCategory(String category, String pageIndex) throws UnirestException;
	HttpResponse<String> getMovieByMovieId(String movieId) throws UnirestException;
	JSONArray getTrailersByMovieId(String movieId) throws UnirestException;
	JSONArray getMoviesByLanguage(String pageIndex, String language) throws UnirestException;

}
