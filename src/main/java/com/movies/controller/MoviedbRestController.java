package com.movies.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.MoviedbService;
import com.movies.controller.service.TicketService;
import com.movies.mapping.Genres;
import com.movies.mapping.MovieDb;
import com.movies.mapping.Person;
import com.movies.mapping.Trailer;
import com.movies.util.MoviesUtil;

@RestController
@RequestMapping("/api")
public class MoviedbRestController {
	
	@Autowired private MoviedbService moviedbService;
	@Autowired private TicketService ticketService;
	
	@GetMapping(value = "/getdbmovies")
	public ResponseEntity<List<MovieDb>> getmoviesdbs(@RequestParam String pageIndex, @RequestParam String search, Model model) throws UnirestException, UnsupportedEncodingException {
		List<MovieDb> movies = new ArrayList<MovieDb>();
		String category = "";
		if(StringUtils.hasText(search)) {
			category = "movie";
		} else {
			category = "popular";
		}
		
		JSONArray jsonArray = moviedbService.getMoviesByCategoryAndSearch(category, search, pageIndex);
		if(jsonArray.length() >0) {
			Gson gson = new Gson();
			Type type = new TypeToken<List<MovieDb>>() {
			}.getType();
			movies = gson.fromJson(jsonArray.toString(), type);
		} 
		return new ResponseEntity<List<MovieDb>>(movies, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getpersons")
	public ResponseEntity<List<Person>> getpersons(@RequestParam String pageIndex, @RequestParam String search) throws UnirestException, UnsupportedEncodingException {
		List<Person> persons = new ArrayList<Person>();
		JSONArray jsonArray = moviedbService.getpersons(pageIndex, search);
		if(jsonArray.length() >0) {
			Gson gson = new Gson();
			Type type = new TypeToken<List<Person>>() {
			}.getType();
			persons = gson.fromJson(jsonArray.toString(), type);
		} 
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = "/getPersonMovies")
	public ResponseEntity<List<MovieDb>> getPersonMovies(@RequestParam String pageIndex, @RequestParam String personId) throws UnirestException, UnsupportedEncodingException {
		List<MovieDb> movies = moviedbService.getpersonmovies(personId);
		return new ResponseEntity<List<MovieDb>>(movies, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getdbmoviesbylanguage")
	public ResponseEntity<List<MovieDb>> getdbmoviesbylanguage(@RequestParam String pageIndex, @RequestParam String language, Model model) throws UnirestException, UnsupportedEncodingException {
		JSONArray jsonArray = moviedbService.getMoviesByLanguage(pageIndex, language);

		Gson gson = new Gson();
		Type type = new TypeToken<List<MovieDb>>() {
		}.getType();
		List<MovieDb> movies = gson.fromJson(jsonArray.toString(), type);
		return new ResponseEntity<List<MovieDb>>(movies, HttpStatus.OK);
	}
	
	@GetMapping(value = "showdbmovie")
	public ResponseEntity<MovieDb> showdbmovie(@RequestParam String movieId, Model model) throws UnirestException {
		HttpResponse<String> movieResponse = moviedbService.getMovieByMovieId(movieId);
		Gson gson = new Gson();
		Type movieType = new TypeToken<MovieDb>() {}.getType();
		MovieDb movie = gson.fromJson(movieResponse.getBody(), movieType);
		if(movie!=null && movie.getGenres()!=null && movie.getGenres().size()>0) {
			String genres =  movie.getGenres().stream()
	                .map(Genres::getName)
	                .collect(Collectors.joining(", "));
			movie.setGenre(genres);
		}
		
		JSONArray movietrailers = moviedbService.getTrailersByMovieId(movieId);
		Type movieTrailer = new TypeToken<List<Trailer>>() {}.getType();
		List<Trailer> trailers = gson.fromJson(movietrailers.toString(), movieTrailer);
		if(trailers!=null && trailers.size()>1) {
			for(Trailer mtrailer : trailers) {
				if(StringUtils.hasText(mtrailer.getType()) && mtrailer.getType().equalsIgnoreCase("Trailer")) {
					movie.setTrailer(mtrailer.getKey());
				}
			}
		} else {
			if(trailers!=null && trailers.size() == 1)  {
				movie.setTrailer(trailers.get(0).getKey());
			}
		}
		return new ResponseEntity<MovieDb>(movie, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "playdbmovie")
	public ResponseEntity<MovieDb> playdbmovie(@RequestParam String movieId, @RequestParam String address, Model model) throws UnirestException, UnknownHostException {
		HttpResponse<String> movieResponse = moviedbService.getMovieByMovieId(movieId);
		Gson gson = new Gson();
		Type movieType = new TypeToken<MovieDb>() {}.getType();
		MovieDb movie = gson.fromJson(movieResponse.getBody(), movieType);
		if(movie!=null && movie.getGenres()!=null && movie.getGenres().size()>0) {
			String genres =  movie.getGenres().stream()
	                .map(Genres::getName)
	                .collect(Collectors.joining(", "));
			movie.setGenre(genres);
		}
		
		JSONArray movietrailers = moviedbService.getTrailersByMovieId(movieId);
		Type movieTrailer = new TypeToken<List<Trailer>>() {}.getType();
		List<Trailer> trailers = gson.fromJson(movietrailers.toString(), movieTrailer);
		if(trailers!=null && trailers.size()>1) {
			for(Trailer mtrailer : trailers) {
				if(StringUtils.hasText(mtrailer.getType()) && mtrailer.getType().equalsIgnoreCase("Trailer")) {
					movie.setTrailer(mtrailer.getKey());
				}
			}
		} else {
			if(trailers!=null && trailers.size() == 1)  {
				movie.setTrailer(trailers.get(0).getKey());
			}
		}
		String movieLink = MoviesUtil.getMovie(movie.getImdb_id());
		if(StringUtils.hasText(movieLink)) {
			movie.setLink(movieLink);
		} else {
			HttpResponse<String> ticketresponse = ticketService.getMovieTricket(movie.getImdb_id(), address);
			movie.setLink( "https://videospider.stream/getvideo?key=5HbImlTRhrrI7aEO&video_id="+movie.getImdb_id()+"&ticket="+ticketresponse.getBody());
		}
		
		return new ResponseEntity<MovieDb>(movie, HttpStatus.OK);
	}	
}
