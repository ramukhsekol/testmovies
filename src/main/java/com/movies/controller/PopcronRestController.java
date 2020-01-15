package com.movies.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.PopcronService;
import com.movies.controller.service.TicketService;
import com.movies.mapping.Movies;

@RestController
@RequestMapping("/api")
public class PopcronRestController {
	
	@Autowired private PopcronService popcronService;
	@Autowired private TicketService ticketService;
	
	@GetMapping(value = "/popular")
	public ResponseEntity<List<Movies>> popular(@RequestParam String pageIndex) throws UnirestException, UnsupportedEncodingException {
		HttpResponse<String> response = popcronService.getMoviesById(pageIndex, "", "");
		Gson gson = new Gson();
		Type type = new TypeToken<List<Movies>>() {
		}.getType();
		List<Movies> movies = gson.fromJson(response.getBody(), type);
		return new ResponseEntity<List<Movies>>(movies, HttpStatus.OK);
	}
	
	@GetMapping("/movierooms")
	public ResponseEntity<List<String>> getmovierooms() throws UnirestException{
		HttpResponse<String> response = popcronService.getmovierooms();
		Gson gson = new Gson();
		Type type = new TypeToken<List<String>>() {}.getType();
		List<String> movierooms = gson.fromJson(response.getBody(), type);
		return new ResponseEntity<List<String>>(movierooms, HttpStatus.OK);
	}
	
	@GetMapping("/movieroom/movies/{movieIndex}")
	public ResponseEntity<List<Movies>> getroommovies(@PathVariable String movieIndex) throws UnirestException {
		List<Movies> movies = new ArrayList<Movies>();
		HttpResponse<String> response = popcronService.getMoviesByIndex(movieIndex);
		if(response.getStatus() == HttpStatus.OK.value()) {
			Gson gson = new Gson();
			Type type = new TypeToken<List<Movies>>() {}.getType();
			movies = gson.fromJson(response.getBody(), type);
		}
		return new ResponseEntity<List<Movies>>(movies, HttpStatus.OK);
	}
	
	@GetMapping("/showpopularmovie")
	public ResponseEntity<Movies> showpopularmovie(@RequestParam String movieId) throws UnirestException {
		Movies movie = new Movies();
		HttpResponse<String> response = popcronService.getMoviesById(movieId);
		if(response.getStatus() == HttpStatus.OK.value()) {
			Gson gson = new Gson();
			Type type = new TypeToken<Movies>() {}.getType();
			movie = gson.fromJson(response.getBody(), type);
		}
		if(movie!=null && movie.getGenres()!=null && movie.getGenres().size()>0) {
			String genre = String.join(", ", movie.getGenres());
			movie.setGenre(genre);
		}
		
		return new ResponseEntity<Movies>(movie, HttpStatus.OK);
	}
	
	@GetMapping("/playpopularmovie")
	public ResponseEntity<Movies> playpopularmovie(@RequestParam String movieId, @RequestParam String ipAddress) throws UnirestException, UnknownHostException {
		Movies movie = new Movies();
		HttpResponse<String> response = popcronService.getMoviesById(movieId);
		if(response.getStatus() == HttpStatus.OK.value()) {
			Gson gson = new Gson();
			Type type = new TypeToken<Movies>() {}.getType();
			movie = gson.fromJson(response.getBody(), type);
			HttpResponse<String> ticketresponse = ticketService.getMovieTricket(movieId, ipAddress);
			movie.setLink("https://videospider.stream/getvideo?key=5HbImlTRhrrI7aEO&video_id="+movie.getImdb_id()+"&ticket="+ticketresponse.getBody());
		}
		if(movie!=null && movie.getGenres()!=null && movie.getGenres().size()>0) {
			String genre = String.join(", ", movie.getGenres());
			movie.setGenre(genre);
		}
		return new ResponseEntity<Movies>(movie, HttpStatus.OK);
	}
	
	@GetMapping(value = "/genresss")
	public ResponseEntity<List<String>> genres() throws UnirestException, UnsupportedEncodingException {
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
		return new ResponseEntity<List<String>>(genres, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getmoviessss")
	public ResponseEntity<List<Movies>> getmovies(@RequestParam String pageIndex, @RequestParam String genre, @RequestParam String keyword) throws UnirestException, UnsupportedEncodingException {
		HttpResponse<String> response = popcronService.getMoviesById(pageIndex, genre, keyword);
		Gson gson = new Gson();
		Type type = new TypeToken<List<Movies>>() {
		}.getType();
		List<Movies> movies = gson.fromJson(response.getBody(), type);
		return new ResponseEntity<List<Movies>>(movies, HttpStatus.OK);
	}
}
