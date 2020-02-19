package com.movies.dto;

import java.util.List;

public class Movie {
  private Long id;
  private String movieName;
  private String movieInfo;
  private String movieDescription;
  private String movieImage;
  private Integer movieYear;
  private String genre;
  private String director;
  private String country;
  private String quality;
  private String rating;
  private String writer;
  private String staring;
  private String language;


  private List<MovieLinks> movieLinks;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMovieName() {
    return movieName;
  }

  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }

  public String getMovieInfo() {
    return movieInfo;
  }

  public void setMovieInfo(String movieInfo) {
    this.movieInfo = movieInfo;
  }

  public String getMovieDescription() {
    return movieDescription;
  }

  public void setMovieDescription(String movieDescription) {
    this.movieDescription = movieDescription;
  }

  public String getMovieImage() {
    return movieImage;
  }

  public void setMovieImage(String movieImage) {
    this.movieImage = movieImage;
  }

  public Integer getMovieYear() {
    return movieYear;
  }

  public void setMovieYear(Integer movieYear) {
    this.movieYear = movieYear;
  }

  public List<MovieLinks> getMovieLinks() {
    return movieLinks;
  }

  public void setMovieLinks(List<MovieLinks> movieLinks) {
    this.movieLinks = movieLinks;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getQuality() {
    return quality;
  }

  public void setQuality(String quality) {
    this.quality = quality;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public String getStaring() {
    return staring;
  }

  public void setStaring(String staring) {
    this.staring = staring;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}
