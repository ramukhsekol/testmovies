package com.movies.util;

import java.util.HashMap;
import java.util.Map;

public class MoviesUtil {
	
	private static Map<String, String> movies = new HashMap<String, String>();
	static {
		movies.put("tt0924317", "https://gounlimited.to/embed-oiv511tmeqj9.html");
		movies.put("tt6836936", "https://gounlimited.to/embed-pp06jwzhwdqk.html");
	}
	
	public static String getMovie(String key) {
		return movies.get(key);
	}
	
	
	

}
