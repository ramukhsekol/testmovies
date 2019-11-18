package com.movies.controller.service;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface MoviedbService {

	JSONArray getMoviesByCategoryAndSearch(String category, String search, String pageIndex) throws UnirestException, UnsupportedEncodingException;
	HttpResponse<String> getMovieByMovieId(String movieId) throws UnirestException;
	JSONArray getTrailersByMovieId(String movieId) throws UnirestException;
	JSONArray getMoviesByLanguage(String pageIndex, String language) throws UnirestException;

}
