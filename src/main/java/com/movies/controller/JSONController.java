package com.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.movies.controller.service.JSONService;
import com.movies.dto.Movie;

@Controller
public class JSONController {
	
	@Autowired private JSONService jsonService;
	
	@GetMapping(value = "generatejson")
	public void genereateJson() {
		int[] missednumbers = {78};
		for(int i = 0; i<missednumbers.length; i++) {
			List<Movie> movies = jsonService.getMovies(missednumbers[i]);
			if(movies!=null && movies.size()>0) {
				boolean isFlag = jsonService.generateJsonFile(missednumbers[i], movies);
				if(isFlag) {
					
				}
			}
		}
		
	}
}