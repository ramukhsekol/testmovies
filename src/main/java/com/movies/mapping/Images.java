package com.movies.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Images {
	
	private String fanart;
	private String banner;
	private String poster;

	public String getFanart() {
		return fanart;
	}

	public void setFanart(String fanart) {
		this.fanart = fanart;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}
}
