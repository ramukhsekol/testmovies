package com.movies.youtube;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Youtube {
	
	private List<Videos> items;

	/**
	 * @return the items
	 */
	public List<Videos> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<Videos> items) {
		this.items = items;
	}

}
