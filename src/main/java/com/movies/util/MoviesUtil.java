package com.movies.util;

import java.util.HashMap;
import java.util.Map;

public class MoviesUtil {
	
	private static Map<String, String> movies = new HashMap<String, String>();
	static {
		movies.put("tt0924317", "https://gounlimited.to/embed-oiv511tmeqj9.html");
		movies.put("tt6836936", "https://gounlimited.to/embed-pp06jwzhwdqk.html");
		movies.put("tt0374497", "https://www.youtube.com/embed/52QtjUD5g74");
		movies.put("tt0858492", "https://www.youtube.com/embed/whIpRskedkQ");
		movies.put("tt0805492", "https://www.youtube.com/embed/a08-34byBDY");
		movies.put("tt1783411", "https://www.youtube.com/embed/NodmtYP9P-g");
		
		
		
	}
	
	public static String getMovie(String key) {
		return movies.get(key);
	}
	
	
	

}
