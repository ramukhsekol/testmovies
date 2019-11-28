package com.movies.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cast {
	private Integer cast_id;
	private String character;
	private Long id;
	private String name;
	private Integer order;
	private String profile_path;

	/**
	 * @return the cast_id
	 */
	public Integer getCast_id() {
		return cast_id;
	}

	/**
	 * @param cast_id the cast_id to set
	 */
	public void setCast_id(Integer cast_id) {
		this.cast_id = cast_id;
	}

	/**
	 * @return the character
	 */
	public String getCharacter() {
		return character;
	}

	/**
	 * @param character the character to set
	 */
	public void setCharacter(String character) {
		this.character = character;
	}

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

	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * @return the profile_path
	 */
	public String getProfile_path() {
		return profile_path;
	}

	/**
	 * @param profile_path the profile_path to set
	 */
	public void setProfile_path(String profile_path) {
		this.profile_path = profile_path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cast other = (Cast) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
