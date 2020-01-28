package com.movies.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
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
import com.movies.mapping.Cast;
import com.movies.mapping.Crew;
import com.movies.mapping.Genres;
import com.movies.mapping.Languages;
import com.movies.mapping.MovieCasting;
import com.movies.mapping.MovieDb;
import com.movies.mapping.Person;
import com.movies.mapping.Trailer;
import com.movies.util.MoviesUtil;

@Controller
public class MoviedbController {
	
	@Autowired private MoviedbService moviedbService;
	@Autowired private TicketService ticketService;
	
	
	@GetMapping("/movies/all")
	public String allmovies(Model model) {
		model.addAttribute("search", "");
		return "view/moviedb/allmovies";
	}
	
	@GetMapping("/dbmoviesearch")
	public String dbmoviesearch(@RequestParam String search, Model model) {
		model.addAttribute("search", search);
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
		model.addAttribute("language", null);
		return "view/moviedb/appendmovies";
	}
	
	
	@GetMapping("/movie/genres")
	public String genres(Model model) throws UnirestException {
		JSONArray jsonArray = moviedbService.getMovieGenres();
		Gson gson = new Gson();
		Type type = new TypeToken<List<Genres>>() {
		}.getType();
		List<Genres> genres = gson.fromJson(jsonArray.toString(), type);
		model.addAttribute("genres", genres);
		return "view/moviedb/moviegenres";
	}
	
	@GetMapping("/genremovies")
	public String genremovies(@RequestParam Integer genreId, Model model) throws UnirestException{
		model.addAttribute("genreId", genreId);
		return "view/moviedb/genremovies";
	}
	
	@GetMapping(value = "/getdbmoviesbygenre")
	public String getdbmoviesbygenre(@RequestParam String pageIndex, @RequestParam Integer genreId, Model model) throws UnirestException, UnsupportedEncodingException {
		JSONArray jsonArray = moviedbService.getMoviesByGenre(pageIndex, genreId);

		Gson gson = new Gson();
		Type type = new TypeToken<List<MovieDb>>() {
		}.getType();
		List<MovieDb> movies = gson.fromJson(jsonArray.toString(), type);
		model.addAttribute("movies", movies);
		model.addAttribute("language", null);
		return "view/moviedb/appendmovies";
	}
	
	
	@GetMapping("/movie/languages")
	public String languages(Model model) throws UnirestException {
		JSONArray jsonArray = moviedbService.getMovieLanguages();
		Gson gson = new Gson();
		Type type = new TypeToken<List<Languages>>() {
		}.getType();
		List<Languages> languages = gson.fromJson(jsonArray.toString(), type);
		model.addAttribute("languages", languages);
		return "view/moviedb/movielanguages";
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
		model.addAttribute("language", language);
		return "view/moviedb/appendmovies";
	}
	
	@GetMapping("/movie/persons")
	public String persons(Model model) throws UnirestException {
		model.addAttribute("search", "");
		return "view/moviedb/moviepersons";
	}
	
	@GetMapping("/searchperson")
	public String searchperson(@RequestParam String search, Model model) {
		model.addAttribute("search", search);
		return "view/moviedb/moviepersons";
	}
	
	@GetMapping(value = "/getpersons")
	public String getpersons(@RequestParam String pageIndex, @RequestParam String search, Model model) throws UnirestException, UnsupportedEncodingException {
		JSONArray jsonArray = moviedbService.getpersons(pageIndex, search);
		if(jsonArray.length() >0) {
			Gson gson = new Gson();
			Type type = new TypeToken<List<Person>>() {
			}.getType();
			List<Person> persons = gson.fromJson(jsonArray.toString(), type);
			model.addAttribute("persons", persons);
		} else {
			model.addAttribute("persons", null);
		}
		return "view/moviedb/appendpersons";
	}
	
	@GetMapping(value = "/getpersonmovies")
	public String getpersonmovies(@RequestParam String personId, Model model) throws UnirestException, UnsupportedEncodingException {
		List<MovieDb> movies  = moviedbService.getpersonmovies(personId);
		model.addAttribute("movies", movies);
		HttpResponse<String> movieResponse = moviedbService.getPersonDetails(personId);
		Gson gson = new Gson();
		Type movieType = new TypeToken<Person>() {}.getType();
		Person person = gson.fromJson(movieResponse.getBody(), movieType);
		model.addAttribute("person", person);
		return "view/moviedb/personmovies";
	}
	
	@GetMapping(value = "showdbmovie")
	public String showdbmovie(@RequestParam String movieId, Model model) throws UnirestException, UnsupportedEncodingException {
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
		
		HttpResponse<String> movieCastResponse = moviedbService.getMovieCastByMovieId(movieId);
		Type movieCast = new TypeToken<MovieCasting>() {}.getType();
		MovieCasting movieCasting = gson.fromJson(movieCastResponse.getBody(), movieCast);
		if(movieCasting!=null) {
			if(movieCasting.getCast()!=null && movieCasting.getCast().size()>0) {
				Collection<Cast> casts = movieCasting.getCast().stream().collect(Collectors.toConcurrentMap(Cast::getName, Function.identity(), (p, q) -> p)).values();
				movieCasting.setCast(casts.stream().collect(Collectors.toCollection(ArrayList::new)));
			}
			if(movieCasting.getCrew()!=null && movieCasting.getCrew().size()>0) {
				Collection<Crew> crews = movieCasting.getCrew().stream().collect(Collectors.toConcurrentMap(Crew::getName, Function.identity(), (p, q) -> p)).values();
				movieCasting.setCrew(crews.stream().collect(Collectors.toCollection(ArrayList::new)));
			}
		}
		model.addAttribute("movieCasting", movieCasting);
		model.addAttribute("movieId", movieId);
		model.addAttribute("trailer", trailer);
		if(movie!=null && StringUtils.hasText(movie.getOriginal_language())) {
			String movieLink = moviedbService.getYoutubeMovies(movie.getTitle());
			if(StringUtils.hasText(movieLink)) {
				model.addAttribute("link", movieLink);
			} else {
				model.addAttribute("link", null);
			}
		} else {
			model.addAttribute("link", null);
		}
		return "view/moviedb/showmovie";
	}
	
	
	@GetMapping(value = "showlangmovie")
	public String showlangmovie(@RequestParam String movieId, Model model) throws UnirestException, UnsupportedEncodingException {
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
		/*
		 * String movieLink = moviedbService.getYoutubeMovies(movie.getTitle());
		 * if(StringUtils.hasText(movieLink)) { model.addAttribute("link", movieLink); }
		 * else { model.addAttribute("link", null); }
		 */
		model.addAttribute("link", null);
		HttpResponse<String> movieCastResponse = moviedbService.getMovieCastByMovieId(movieId);
		Type movieCast = new TypeToken<MovieCasting>() {}.getType();
		MovieCasting movieCasting = gson.fromJson(movieCastResponse.getBody(), movieCast);
		if(movieCasting!=null) {
			if(movieCasting.getCast()!=null && movieCasting.getCast().size()>0) {
				Collection<Cast> casts = movieCasting.getCast().stream().collect(Collectors.toConcurrentMap(Cast::getName, Function.identity(), (p, q) -> p)).values();
				movieCasting.setCast(casts.stream().collect(Collectors.toCollection(ArrayList::new)));
			}
			if(movieCasting.getCrew()!=null && movieCasting.getCrew().size()>0) {
				Collection<Crew> crews = movieCasting.getCrew().stream().collect(Collectors.toConcurrentMap(Crew::getName, Function.identity(), (p, q) -> p)).values();
				movieCasting.setCrew(crews.stream().collect(Collectors.toCollection(ArrayList::new)));
			}
		}
		
		model.addAttribute("movieCasting", movieCasting);
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
		
		HttpResponse<String> movieCastResponse = moviedbService.getMovieCastByMovieId(movieId);
		Type movieCast = new TypeToken<MovieCasting>() {}.getType();
		MovieCasting movieCasting = gson.fromJson(movieCastResponse.getBody(), movieCast);
		if(movieCasting!=null) {
			if(movieCasting.getCast()!=null && movieCasting.getCast().size()>0) {
				Collection<Cast> casts = movieCasting.getCast().stream().collect(Collectors.toConcurrentMap(Cast::getName, Function.identity(), (p, q) -> p)).values();
				movieCasting.setCast(casts.stream().collect(Collectors.toCollection(ArrayList::new)));
			}
			if(movieCasting.getCrew()!=null && movieCasting.getCrew().size()>0) {
				Collection<Crew> crews = movieCasting.getCrew().stream().collect(Collectors.toConcurrentMap(Crew::getName, Function.identity(), (p, q) -> p)).values();
				movieCasting.setCrew(crews.stream().collect(Collectors.toCollection(ArrayList::new)));
			}
		}
		model.addAttribute("movieCasting", movieCasting);
		
		return "view/moviedb/playmovie";
	}	
}
