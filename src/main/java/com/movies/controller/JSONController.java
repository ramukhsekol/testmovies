package com.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.movies.controller.service.JSONService;
import com.movies.dto.Alphamovies;
import com.movies.dto.Movie;

@Controller
public class JSONController {
	
	@Autowired private JSONService jsonService;
	
	@GetMapping(value = "generatejson")
	public void genereateJson() {
		int[] missednumbers = {11,12,13,14,15,16,17,18,19,20};
		for(int i = 0; i<missednumbers.length; i++) {
			List<Movie> movies = jsonService.getMovies(missednumbers[i]);
			if(movies!=null && !movies.isEmpty()) {
				jsonService.generateJsonFile(missednumbers[i], movies);
			}
		}
	}
	
	@GetMapping(value = "generatealphajson")
	public void testmovies() {
		int[] missednumbers = {12,13,16,17,20,21,22,23,24};
		for(int i = 0; i<missednumbers.length; i++) {
			List<Alphamovies> movies = jsonService.getAplhaMovies(missednumbers[i]);
			if(movies!=null && !movies.isEmpty()) {
				jsonService.generateAlphaJsonFile(missednumbers[i], movies);
			}
		}	

	}	
}
