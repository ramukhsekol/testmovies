package com.movies.controller.service;

import java.util.List;

import com.movies.dto.Alphamovies;
import com.movies.dto.Movie;

public interface JSONService {

	List<Movie> getMovies(int index);
	boolean generateJsonFile(int index, List<Movie> movies);
	List<Movie> getMoviesByIndex(String pageIndex);
	List<Alphamovies> getAplhaMovies(int index);
	boolean generateAlphaJsonFile(int i, List<Alphamovies> movies);
	List<Alphamovies> getMoviesByIndex(String movietype, String pageIndex);

}
