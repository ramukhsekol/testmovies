package com.movies.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating {
	private String loved;
	private String percentage;
	private String votes;
	private String watching;
	private String hated;

	public String getLoved() {
		return loved;
	}

	public void setLoved(String loved) {
		this.loved = loved;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getVotes() {
		return votes;
	}

	public void setVotes(String votes) {
		this.votes = votes;
	}

	public String getWatching() {
		return watching;
	}

	public void setWatching(String watching) {
		this.watching = watching;
	}

	public String getHated() {
		return hated;
	}

	public void setHated(String hated) {
		this.hated = hated;
	}
}
