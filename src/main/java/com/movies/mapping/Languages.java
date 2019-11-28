package com.movies.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Languages {
	private String iso_639_1;
	private String english_name;
	private String name;

	/**
	 * @return the iso_639_1
	 */
	public String getIso_639_1() {
		return iso_639_1;
	}

	/**
	 * @param iso_639_1 the iso_639_1 to set
	 */
	public void setIso_639_1(String iso_639_1) {
		this.iso_639_1 = iso_639_1;
	}

	/**
	 * @return the english_name
	 */
	public String getEnglish_name() {
		return english_name;
	}

	/**
	 * @param english_name the english_name to set
	 */
	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
