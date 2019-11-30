package com.movies.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoviesUtil {
	
	private static Map<String, String> movies = new HashMap<String, String>();
	private static List<String> movieNames = new ArrayList<>();
	
	
	static {
		movies.put("tt0924317", "https://gounlimited.to/embed-oiv511tmeqj9.html");	
		
		
		movieNames.add("Ee Abbai Chala Manchodu");
		movieNames.add("Baladur");
		movieNames.add("Magadheera");
		movieNames.add("Gang Leader");
		movieNames.add("Mosagadu");
		
		
		
		
		
		
		
	}
	
	public static String getMovie(String key) {
		return movies.get(key);
	}
	
	public static boolean isMovieExist(String movieName) {
		return movieNames.contains(movieName);
	}
	
	
	

}
