package com.movies.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;

public class IpAddressUtil {

	public static String getPublicIpAddress() throws UnknownHostException {
        String systemipaddress = ""; 
        try
        { 
            URL url_name = new URL("https://ipapi.co/json/"); 
            BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream())); 
            systemipaddress = sc.readLine().trim(); 
        } 
        catch (Exception e) 
        { 
            systemipaddress = "Cannot Execute Properly"; 
        } 
        System.out.println(systemipaddress);
        return "175.100.151.150";
	}

}
