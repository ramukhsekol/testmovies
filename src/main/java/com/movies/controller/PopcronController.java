package com.movies.controller;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.PopcronService;
import com.movies.mapping.Movies;

@Controller
public class PopcronController {
	
	@Autowired private PopcronService popcronService;
	
	@GetMapping("/")
	public String getmovierooms(Model model) throws UnirestException{
		HttpResponse<String> response = popcronService.getmovierooms();
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<String>>() {}.getType();
		List<String> movierooms = gson.fromJson(response.getBody(), type);
		
		model.addAttribute("movierooms", movierooms);
		return "view/movierooms";
	}
	
	
	@GetMapping("/getmovieroom/movies/{movieIndex}")
	public String getroommovies(@PathVariable String movieIndex, Model model) throws UnirestException{
		HttpResponse<String> response = popcronService.getMoviesByIndex(movieIndex);
		if(response.getStatus() == HttpStatus.OK.value()) {
			Gson gson = new Gson();
			Type type = new TypeToken<List<Movies>>() {}.getType();
			List<Movies> movies = gson.fromJson(response.getBody(), type);
			model.addAttribute("movies", movies);
		}
		return "view/movies";
	}
	
	@GetMapping("/showmovie")
	public String showmovie(@RequestParam String movieId, Model model) throws UnirestException{
		HttpResponse<String> response = popcronService.getMoviesById(movieId);
		if(response.getStatus() == HttpStatus.OK.value()) {
			Gson gson = new Gson();
			Type type = new TypeToken<Movies>() {}.getType();
			Movies movie = gson.fromJson(response.getBody(), type);
			model.addAttribute("movie", movie);
		}
		return "view/showmovie";
	}
	
	@GetMapping("/playmovie")
	public String playmovie(@RequestParam String movieId, Model model) throws UnirestException, UnknownHostException {
		HttpResponse<String> response = popcronService.getMoviesById(movieId);
		if(response.getStatus() == HttpStatus.OK.value()) {
			Gson gson = new Gson();
			Type type = new TypeToken<Movies>() {}.getType();
			Movies movie = gson.fromJson(response.getBody(), type);
			HttpResponse<String> ticketresponse = popcronService.getMovieTricket(movieId);
			System.out.println(ticketresponse.getBody());
			model.addAttribute("link", "https://videospider.stream/getvideo?key=5HbImlTRhrrI7aEO&video_id="+movie.getImdb_id()+"&ticket="+ticketresponse.getBody());
			model.addAttribute("movie", movie);
		}
		return "view/playmovie";
	}
}
