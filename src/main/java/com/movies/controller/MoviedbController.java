package com.movies.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.MoviedbService;
import com.movies.controller.service.TicketService;
import com.movies.mapping.Genres;
import com.movies.mapping.MovieDb;
import com.movies.mapping.Trailer;
import com.movies.util.MoviesUtil;

@Controller
public class MoviedbController {
	
	@Autowired private MoviedbService moviedbService;
	@Autowired private TicketService ticketService;
	
	@GetMapping("/popular")
	public String popular(Model model) {
		model.addAttribute("search", "");
		return "view/moviedb/allmovies";
	}
	
	@GetMapping("/dbmoviesearch")
	public String dbmoviesearch(@RequestParam String search, Model model) {
		model.addAttribute("search", search);
		model.addAttribute("norecords", false);
		return "view/moviedb/allmovies";
	}
	
	
	@GetMapping(value = "/getdbmovies")
	public String getmoviesdbs(@RequestParam String pageIndex, @RequestParam String search, Model model) throws UnirestException, UnsupportedEncodingException {
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
			List<MovieDb> movies = gson.fromJson(jsonArray.toString(), type);
			model.addAttribute("movies", movies);
		} else {
			model.addAttribute("movies", null);
		}
		return "view/moviedb/appendmovies";
	}
	
	
	@GetMapping("/moviesbylanguage")
	public String moviebylanguage(@RequestParam String language, Model model) throws UnirestException {
		model.addAttribute("language", language);
		return "view/moviedb/languagemovies";
	}
	
	
	@GetMapping(value = "/getdbmoviesbylanguage")
	public String getdbmoviesbylanguage(@RequestParam String pageIndex, @RequestParam String language, Model model) throws UnirestException, UnsupportedEncodingException {
		JSONArray jsonArray = moviedbService.getMoviesByLanguage(pageIndex, language);

		Gson gson = new Gson();
		Type type = new TypeToken<List<MovieDb>>() {
		}.getType();
		List<MovieDb> movies = gson.fromJson(jsonArray.toString(), type);
		model.addAttribute("movies", movies);
		return "view/moviedb/appendmovies";
	}
	
	@GetMapping(value = "showdbmovie")
	public String showdbmovie(@RequestParam String movieId, Model model) throws UnirestException {
		HttpResponse<String> movieResponse = moviedbService.getMovieByMovieId(movieId);
		Gson gson = new Gson();
		Type movieType = new TypeToken<MovieDb>() {}.getType();
		MovieDb movie = gson.fromJson(movieResponse.getBody(), movieType);
		model.addAttribute("movie", movie);
		if(movie!=null && movie.getGenres()!=null && movie.getGenres().size()>0) {
			String genres =  movie.getGenres().stream()
	                .map(Genres::getName)
	                .collect(Collectors.joining(", "));
			model.addAttribute("genres", genres);
		} else {
			model.addAttribute("genres", null);
		}

		JSONArray movietrailers = moviedbService.getTrailersByMovieId(movieId);
		Type movieTrailer = new TypeToken<List<Trailer>>() {}.getType();
		List<Trailer> trailers = gson.fromJson(movietrailers.toString(), movieTrailer);
		String trailer = "";
		if(trailers!=null && trailers.size()>1) {
			for(Trailer mtrailer : trailers) {
				if(StringUtils.hasText(mtrailer.getType()) && mtrailer.getType().equalsIgnoreCase("Trailer")) {
					trailer = mtrailer.getKey();
				}
			}
		} else {
			if(trailers!=null && trailers.size() == 1)  {
				trailer = trailers.get(0).getKey();
			}
		}
		model.addAttribute("movieId", movieId);
		model.addAttribute("trailer", trailer);
		return "view/moviedb/showmovie";
	}
	
	@GetMapping(value = "playdbmovie")
	public String playdbmovie(@RequestParam String movieId, @RequestParam String address, Model model) throws UnirestException, UnknownHostException {
		HttpResponse<String> movieResponse = moviedbService.getMovieByMovieId(movieId);
		Gson gson = new Gson();
		Type movieType = new TypeToken<MovieDb>() {}.getType();
		MovieDb movie = gson.fromJson(movieResponse.getBody(), movieType);
		model.addAttribute("movie", movie);
		if(movie!=null && movie.getGenres()!=null && movie.getGenres().size()>0) {
			String genres =  movie.getGenres().stream()
	                .map(Genres::getName)
	                .collect(Collectors.joining(", "));
			model.addAttribute("genres", genres);
		} else {
			model.addAttribute("genres", null);
		}
		
		JSONArray movietrailers = moviedbService.getTrailersByMovieId(movieId);
		Type movieTrailer = new TypeToken<List<Trailer>>() {}.getType();
		List<Trailer> trailers = gson.fromJson(movietrailers.toString(), movieTrailer);
		String trailer = "";
		if(trailers!=null && trailers.size()>0) {
			for(Trailer mtrailer : trailers) {
				if(StringUtils.hasText(mtrailer.getType()) && mtrailer.getType().equalsIgnoreCase("Trailer")) {
					trailer = mtrailer.getKey();
				}
			}
		}
		
		model.addAttribute("trailer", trailer);
		String movieLink = MoviesUtil.getMovie(movie.getImdb_id());
		if(StringUtils.hasText(movieLink)) {
			model.addAttribute("link", movieLink);
		} else {
			HttpResponse<String> ticketresponse = ticketService.getMovieTricket(movie.getImdb_id(), address);
			model.addAttribute("link", "https://videospider.stream/getvideo?key=5HbImlTRhrrI7aEO&video_id="+movie.getImdb_id()+"&ticket="+ticketresponse.getBody());
		}
		
		return "view/moviedb/playmovie";
	}	
}
