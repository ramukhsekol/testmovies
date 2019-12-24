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
import com.movies.mapping.Cast;
import com.movies.mapping.Crew;
import com.movies.mapping.Genres;
import com.movies.mapping.Languages;
import com.movies.mapping.MovieCasting;
import com.movies.mapping.MovieDb;
import com.movies.mapping.Person;
import com.movies.mapping.Trailer;
import com.movies.util.MoviesUtil;

@RestController
@RequestMapping("/api")
public class MoviedbRestController {
	
	@Autowired private MoviedbService moviedbService;
	@Autowired private TicketService ticketService;
	
	@GetMapping(value = "/getmovies")
	public ResponseEntity<List<MovieDb>> getmovies(@RequestParam String pageIndex, @RequestParam String search, @RequestParam String genreId, @RequestParam String language, Model model) throws UnirestException, UnsupportedEncodingException {
		List<MovieDb> movies = new ArrayList<MovieDb>();
		JSONArray jsonArray = new JSONArray();
		String category = StringUtils.hasText(search)?"movie": "popular";
		if(StringUtils.hasText(genreId)) {
			jsonArray = moviedbService.getMoviesByGenre(pageIndex, Integer.parseInt(genreId));
		} else if(StringUtils.hasText(language)) {
			jsonArray = moviedbService.getMoviesByLanguage(pageIndex, language);
		} else {
			jsonArray = moviedbService.getMoviesByCategoryAndSearch(category, search, pageIndex);
		}
		 
		if(jsonArray.length() >0) {
			Gson gson = new Gson();
			Type type = new TypeToken<List<MovieDb>>() {
			}.getType();
			movies = gson.fromJson(jsonArray.toString(), type);
		} 
		return new ResponseEntity<List<MovieDb>>(movies, HttpStatus.OK);
	}
	
	@GetMapping("/genres")
	public ResponseEntity<List<Genres>> genres(Model model) throws UnirestException {
		JSONArray jsonArray = moviedbService.getMovieGenres();
		Gson gson = new Gson();
		Type type = new TypeToken<List<Genres>>() {
		}.getType();
		List<Genres> genres = gson.fromJson(jsonArray.toString(), type);
		return new ResponseEntity<List<Genres>>(genres, HttpStatus.OK);
	}
	
	@GetMapping("/languages")
	public ResponseEntity<List<Languages>> languages(Model model) throws UnirestException {
		JSONArray jsonArray = moviedbService.getMovieLanguages();
		Gson gson = new Gson();
		Type type = new TypeToken<List<Languages>>() {
		}.getType();
		List<Languages> languages = gson.fromJson(jsonArray.toString(), type);
		return new ResponseEntity<List<Languages>>(languages, HttpStatus.OK);
	}
	
	@GetMapping(value = "/persons")
	public ResponseEntity<List<Person>> persons(@RequestParam String pageIndex, @RequestParam String search) throws UnirestException, UnsupportedEncodingException {
		JSONArray jsonArray = moviedbService.getpersons(pageIndex, search);
		Gson gson = new Gson();
		Type type = new TypeToken<List<Person>>() {
		}.getType();
		List<Person> persons = gson.fromJson(jsonArray.toString(), type);
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	
	}
	
	@GetMapping(value = "/getPerson")
	public ResponseEntity<Person> getPerson(@RequestParam String personId, Model model) throws UnirestException, UnsupportedEncodingException {
		HttpResponse<String> movieResponse = moviedbService.getPersonDetails(personId);
		Gson gson = new Gson();
		Type movieType = new TypeToken<Person>() {}.getType();
		Person person = gson.fromJson(movieResponse.getBody(), movieType);
		if(person!=null) {
			List<MovieDb> movies  = moviedbService.getpersonmovies(personId);
			person.setMovies(movies);
		}
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "showmovie")
	public ResponseEntity<MovieDb> showmovie(@RequestParam String movieId) throws UnirestException, UnsupportedEncodingException {
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
		movie.setMovieCasting(movieCasting);
		movie.setMovieId(movieId);
		if(movie!=null && StringUtils.hasText(movie.getOriginal_language()) && (movie.getOriginal_language().equalsIgnoreCase("te") || MoviesUtil.isMovieExist(movie.getOriginal_title()))) {
			String movieLink = moviedbService.getYoutubeMovies(movie.getTitle());
			if(StringUtils.hasText(movieLink)) {
				movie.setLink(movieLink);
			} else {
				movie.setLink(null);
			}
		} else {
			movie.setLink(null);
		}
		return new ResponseEntity<MovieDb>(movie, HttpStatus.OK);
	}
	
	@GetMapping(value = "playmovie")
	public ResponseEntity<MovieDb> playmovie(@RequestParam String movieId, @RequestParam String ipAddress) throws UnirestException, UnknownHostException {
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
		movie.setMovieCasting(movieCasting);
		movie.setMovieId(movieId);
		
		String movieLink = MoviesUtil.getMovie(movie.getImdb_id());
		if(StringUtils.hasText(movieLink)) {
			movie.setLink(movieLink);
		} else {
			HttpResponse<String> ticketresponse = ticketService.getMovieTricket(movie.getImdb_id(), ipAddress);
			movie.setLink( "https://videospider.stream/getvideo?key=5HbImlTRhrrI7aEO&video_id="+movie.getImdb_id()+"&ticket="+ticketresponse.getBody());
		}
		
		return new ResponseEntity<MovieDb>(movie, HttpStatus.OK);
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
