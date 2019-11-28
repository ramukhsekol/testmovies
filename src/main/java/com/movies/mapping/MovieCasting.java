package com.movies.mapping;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieCasting {

	private Long id;
	private List<Cast> cast;
	private List<Crew> crew;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cast
	 */
	public List<Cast> getCast() {
		return cast;
	}

	/**
	 * @param cast the cast to set
	 */
	public void setCast(List<Cast> cast) {
		this.cast = cast;
	}

	/**
	 * @return the crew
	 */
	public List<Crew> getCrew() {
		return crew;
	}

	/**
	 * @param crew the crew to set
	 */
	public void setCrew(List<Crew> crew) {
		this.crew = crew;
	}

}
