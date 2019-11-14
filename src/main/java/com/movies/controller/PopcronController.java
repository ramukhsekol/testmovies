package com.movies.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
import com.movies.controller.service.TicketService;
import com.movies.mapping.Movies;

@Controller
public class PopcronController {
	
	@Autowired private PopcronService popcronService;
	@Autowired private TicketService ticketService;
	
	@GetMapping("/")
	public String movies() throws UnirestException{
		return "redirect:/movies";
	}
	
	@GetMapping("/movies")
	public String movies(Model model) throws UnirestException{
		model.addAttribute("genre", "");
		return "view/popcron/allmovies";
	}
	
	@GetMapping(value = "/genres")
	public String genres(Model model) throws UnirestException, UnsupportedEncodingException {
		List<String> genres = new ArrayList<String>();
		genres.add("Action");
		genres.add("Adventure");
		genres.add("Animation");
		genres.add("Biography");
		genres.add("Comedy");
		genres.add("Crime");
		genres.add("Documentary");
		genres.add("Drama");
		genres.add("Family");
		genres.add("Fantasy");
		genres.add("Film-Noir");
		genres.add("History");
		genres.add("Horror");
		genres.add("Music");
		genres.add("Musical");
		genres.add("Mystery");
		genres.add("Romance");
		genres.add("Sci-Fi");
		genres.add("Short");
		genres.add("Suspense");
		genres.add("Thriller");
		genres.add("War");
		genres.add("Western");
		model.addAttribute("genres", genres);
		return "view/popcron/genres";
	}
	
	@GetMapping("/getgenremovies")
	public String movies(@RequestParam String genre, Model model) throws UnirestException{
		model.addAttribute("genre", genre);
		return "view/popcron/allmovies";
	}
	
	
	@GetMapping(value = "/getmovies")
	public String getmovies(@RequestParam String pageIndex, @RequestParam String genre, Model model) throws UnirestException, UnsupportedEncodingException {
		HttpResponse<String> response = popcronService.getMoviesById(pageIndex, genre);
		Gson gson = new Gson();
		Type type = new TypeToken<List<Movies>>() {
		}.getType();
		List<Movies> movies = gson.fromJson(response.getBody(), type);
		model.addAttribute("movies", movies);
		return "view/popcron/appendmovies";
	}
	
	
	@GetMapping("/movierooms")
	public String getmovierooms(Model model) throws UnirestException{
		HttpResponse<String> response = popcronService.getmovierooms();
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<String>>() {}.getType();
		List<String> movierooms = gson.fromJson(response.getBody(), type);
		
		model.addAttribute("movierooms", movierooms);
		return "view/popcron/movierooms";
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
		return "view/popcron/movies";
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
		return "view/popcron/showmovie";
	}
	
	@GetMapping("/playmovie")
	public String playmovie(@RequestParam String movieId, Model model) throws UnirestException, UnknownHostException {
		HttpResponse<String> response = popcronService.getMoviesById(movieId);
		if(response.getStatus() == HttpStatus.OK.value()) {
			Gson gson = new Gson();
			Type type = new TypeToken<Movies>() {}.getType();
			Movies movie = gson.fromJson(response.getBody(), type);
			HttpResponse<String> ticketresponse = ticketService.getMovieTricket(movieId);
			System.out.println(ticketresponse.getBody());
			model.addAttribute("link", "https://videospider.stream/getvideo?key=5HbImlTRhrrI7aEO&video_id="+movie.getImdb_id()+"&ticket="+ticketresponse.getBody());
			model.addAttribute("movie", movie);
		}
		return "view/popcron/playmovie";
	}
}
