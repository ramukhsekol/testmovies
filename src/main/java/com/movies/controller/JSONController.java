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
		int[] missednumbers = {1};
		for(int i = 0; i<missednumbers.length; i++) {
			List<Movie> movies = jsonService.getMovies(missednumbers[i]);
			if(movies!=null && movies.size()>0) {
				boolean isFlag = jsonService.generateJsonFile(0, movies);
				if(isFlag) {
					
				}
			}
		}
	}
	
	@GetMapping(value = "generatealphajson")
	public void testmovies() {
		int[] missednumbers = {1,2};
		for(int i = 0; i<missednumbers.length; i++) {
			List<Alphamovies> movies = jsonService.getAplhaMovies(missednumbers[i]);
			if(movies!=null && movies.size()>0) {
				boolean isFlag = jsonService.generateAlphaJsonFile(missednumbers[i], movies);
				if(isFlag) {
					
				}
			}
		}	

	}	
}
