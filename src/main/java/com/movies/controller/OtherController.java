package com.movies.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.JSONService;
import com.movies.dto.Movie;

@Controller
public class OtherController {
	
	@Autowired private JSONService jsonService;
	
	@GetMapping("/movie/others")
	public String dbmoviesearch(Model model) {
		model.addAttribute("keyword", "");
		return "view/others/allmovies";
	}
	
	@GetMapping(value = "/getothermovies")
	public String getothermovies(@RequestParam String pageIndex, Model model) {
		List<Movie> dbmovies = jsonService.getMoviesByIndex(pageIndex);
		List<Movie> movies =  dbmovies.stream().filter(m -> (m.getMovieLinks()!=null && m.getMovieLinks().size()>0)).collect(Collectors.toList());  
		model.addAttribute("movies", movies);
		model.addAttribute("pageIndex", pageIndex);
		return "view/others/appendmovies";
	}
	
	@GetMapping("/showothermovie")
	public String showothermovie(@RequestParam Long movieId, @RequestParam String pageIndex, Model model) throws UnirestException{
		List<Movie> dbmovies = jsonService.getMoviesByIndex(pageIndex);
		List<Movie> movies =  dbmovies.stream().filter(m -> m.getId() == movieId).collect(Collectors.toList());  
		if(movies!=null && movies.size()>0) {
			Movie movie = movies.get(0);
			if(movie!=null && StringUtils.hasText(movie.getMovieInfo())) {
				List<String> descriptions = Arrays.asList(movie.getMovieInfo().split("<br>"));
				model.addAttribute("descriptions", descriptions);
			}
			model.addAttribute("movie", movie);
		}
		return "view/others/showmovie";
	}
	

}
