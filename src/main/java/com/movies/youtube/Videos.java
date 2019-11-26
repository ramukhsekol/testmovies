package com.movies.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Videos {

	private VideoId id;
	private VideoTitle snippet;

	/**
	 * @return the id
	 */
	public VideoId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(VideoId id) {
		this.id = id;
	}

	/**
	 * @return the snippet
	 */
	public VideoTitle getSnippet() {
		return snippet;
	}

	/**
	 * @param snippet the snippet to set
	 */
	public void setSnippet(VideoTitle snippet) {
		this.snippet = snippet;
	}

}
