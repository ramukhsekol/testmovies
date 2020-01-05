package com.movies.controller.service.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.MoviedbService;
import com.movies.mapping.MovieDb;
import com.movies.youtube.Videos;
import com.movies.youtube.Youtube;

@Service
public class MoviedbServiceImpl implements MoviedbService {

	@Override
	public JSONArray getMoviesByCategoryAndSearch(String category, String search, String pageIndex) throws UnirestException, UnsupportedEncodingException {
		String url = "";
		if(StringUtils.hasText(search)) {
			url = "https://api.themoviedb.org/3/search/"+category+"?api_key=663337055530cc77a3aa1d26fec365d5&query="+URLEncoder.encode(search, "UTF-8")+"&page=" + pageIndex;
		} else {
			url = "https://api.themoviedb.org/3/movie/"+category+"?api_key=663337055530cc77a3aa1d26fec365d5&page=" + pageIndex;
		}
		
		HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
		JSONArray jsonArray = jsonResponse.getBody().getObject().getJSONArray("results");
		return jsonArray;
	}

	@Override
	public HttpResponse<String> getMovieByMovieId(String movieId) throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=663337055530cc77a3aa1d26fec365d5").asString();
		return response;
	}

	@Override
	public JSONArray getTrailersByMovieId(String movieId) throws UnirestException {
		HttpResponse<JsonNode> jsonResponse = Unirest.get("https://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=663337055530cc77a3aa1d26fec365d5").asJson();
		JSONArray movietrailers = jsonResponse.getBody().getObject().getJSONArray("results");
		return movietrailers;
	}

	@Override
	public JSONArray getMoviesByLanguage(String pageIndex, String language) throws UnirestException {
	String url = "https://api.themoviedb.org/3/discover/movie?api_key=663337055530cc77a3aa1d26fec365d5&page=" + pageIndex + "&with_original_language=" + language;
	HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
	JSONArray jsonArray = jsonResponse.getBody().getObject().getJSONArray("results");
	return jsonArray;
	}

	@Override
	public JSONArray getpersons(String pageIndex, String search) throws UnirestException, UnsupportedEncodingException {
		String url = "";
		if(StringUtils.hasText(search)) {
			url = "https://api.themoviedb.org/3/search/person?api_key=663337055530cc77a3aa1d26fec365d5&query="+URLEncoder.encode(search, "UTF-8")+"&page=" + pageIndex;
		} else {
			url = "https://api.themoviedb.org/3/person/popular?api_key=663337055530cc77a3aa1d26fec365d5&page=" + pageIndex;
		}
		
		HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
		JSONArray jsonArray = jsonResponse.getBody().getObject().getJSONArray("results");
		return jsonArray;
	}

	@Override
	public List<MovieDb> getpersonmovies(String personId)	throws UnirestException, UnsupportedEncodingException {
		String url = "https://api.themoviedb.org/3/person/" + personId
				+ "/movie_credits?api_key=663337055530cc77a3aa1d26fec365d5";
		HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
		JSONArray cast = jsonResponse.getBody().getObject().getJSONArray("cast");
		JSONArray crew = jsonResponse.getBody().getObject().getJSONArray("crew");
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<MovieDb>>() {
		}.getType();
		List<MovieDb> castmovies = gson.fromJson(cast.toString(), type);
		List<MovieDb> crewmovies = gson.fromJson(crew.toString(), type);
		castmovies.addAll(crewmovies);
		
		List<MovieDb> movies = new ArrayList<MovieDb>();
		List<Long> duplicates = new ArrayList<Long>();
		if(castmovies!=null && castmovies.size()>0) {
			for(MovieDb movie : castmovies) {
				if(!duplicates.contains(movie.getId())) {
					duplicates.add(movie.getId());
					movies.add(movie);
				}
			}
		}
		return movies;
	}

	@Override
	public String getYoutubeMovies(String title) throws UnirestException, UnsupportedEncodingException {
		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=50&q="+URLEncoder.encode(title, "UTF-8")+"&key=AIzaSyD70ZIfWEykYMxBWx69-XQpLFWiiZy-aH8";
		
		HttpResponse<String> response  = Unirest.get(url).asString();
		String link = null;
		Gson gson = new Gson();
		Type type = new TypeToken<Youtube>() {}.getType();
		Youtube movie = gson.fromJson(response.getBody(), type);
		if(movie!=null && movie.getItems()!=null && movie.getItems().size()>0) {
			for(Videos videos : movie.getItems()) {
				if(videos.getSnippet()!=null && StringUtils.hasText(videos.getSnippet().getTitle()) && !videos.getSnippet().getTitle().contains("Telugu Full Movie Scenes") &&
						(videos.getSnippet().getTitle().contains("Full Telugu Movie") || videos.getSnippet().getTitle().contains("Telugu Full Movie") || videos.getSnippet().getTitle().contains("Full Comedy Movie") || videos.getSnippet().getTitle().contains("Telugu Full HD Movie") ||  videos.getSnippet().getTitle().contains("Telugu Movie Full HD") ||
								videos.getSnippet().getTitle().contains("Telugu Full Length HD Movie") || videos.getSnippet().getTitle().contains("Telugu Full Length Comedy Entertainer")
						|| videos.getSnippet().getTitle().contains("Telugu Full Length Movie") || videos.getSnippet().getTitle().contains("Full Movie") || videos.getSnippet().getTitle().contains("Full Length Telugu Movie") || videos.getSnippet().getTitle().contains("full HD movie"))) {
					if(videos.getId()!=null && StringUtils.hasText(videos.getId().getVideoId())) {
						return videos.getId().getVideoId();
					}
					
				}
			}
		}
		return link;
	}

	@Override
	public JSONArray getMovieLanguages() throws UnirestException {
		String url = "https://api.themoviedb.org/3/configuration/languages?api_key=663337055530cc77a3aa1d26fec365d5";
		HttpResponse<JsonNode> jsonResponse  = Unirest.get(url).asJson();
		JSONArray response = jsonResponse.getBody().getArray();
		return response;
	}

	@Override
	public JSONArray getMovieGenres() throws UnirestException {
		String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=663337055530cc77a3aa1d26fec365d5";
		HttpResponse<JsonNode> jsonResponse  = Unirest.get(url).asJson();
		JSONArray response = jsonResponse.getBody().getObject().getJSONArray("genres");
		return response;
	}

	@Override
	public JSONArray getMoviesByGenre(String pageIndex, Integer genreId) throws UnirestException {
		String url = "https://api.themoviedb.org/3/discover/movie?api_key=663337055530cc77a3aa1d26fec365d5&page=" + pageIndex + "&with_genres=" + genreId;
		HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
		JSONArray jsonArray = jsonResponse.getBody().getObject().getJSONArray("results");
		return jsonArray;
	}

	@Override
	public HttpResponse<String> getMovieCastByMovieId(String movieId) throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/movie/"+movieId+"/credits?api_key=663337055530cc77a3aa1d26fec365d5").asString();
		return response;
	}

	@Override
	public HttpResponse<String> getPersonDetails(String personId) throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/person/"+personId+"?api_key=663337055530cc77a3aa1d26fec365d5").asString();
		return response;
	}

}
