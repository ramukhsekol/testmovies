package com.movies.mapping;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movies {
	private Images images;
	private String imdb_id;
	private String year;
	private Rating rating;
	private String runtime;
	private String synopsis;
	private String title;
	private String certification;
	private String trailer;
	private List<String> genres;
	private String _id;
	private String released;

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public String getImdb_id() {
		return imdb_id;
	}

	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public List<String> getGenres() {
		return genres;
	}

	
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}
}
