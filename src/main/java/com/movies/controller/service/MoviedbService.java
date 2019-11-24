package com.movies.controller.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.json.JSONArray;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.mapping.MovieDb;

public interface MoviedbService {

	JSONArray getMoviesByCategoryAndSearch(String category, String search, String pageIndex) throws UnirestException, UnsupportedEncodingException;
	HttpResponse<String> getMovieByMovieId(String movieId) throws UnirestException;
	JSONArray getTrailersByMovieId(String movieId) throws UnirestException;
	JSONArray getMoviesByLanguage(String pageIndex, String language) throws UnirestException;
	JSONArray getpersons(String pageIndex, String search) throws UnirestException, UnsupportedEncodingException;
	List<MovieDb> getpersonmovies(String pageIndex, String personId)  throws UnirestException, UnsupportedEncodingException;

}
