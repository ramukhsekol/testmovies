package com.movies.util;

import java.net.UnknownHostException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class IpAddressUtil {

	public static String getPublicIpAddress() throws UnknownHostException {
        String systemipaddress = ""; 
        try
        { 
        	HttpResponse<JsonNode> response = Unirest.get("https://ipapi.co/json/").asJson();
        	System.out.println(response.getBody().getObject().get("ip"));
        	systemipaddress = (String) response.getBody().getObject().get("ip");
			/*
			 * URL url_name = new URL("https://ipapi.co/json/"); BufferedReader sc = new
			 * BufferedReader(new InputStreamReader(url_name.openStream())); systemipaddress
			 * = sc.readLine().trim();
			 */
        } 
        catch (Exception e) 
        { 
            systemipaddress = "Cannot Execute Properly"; 
        } 
        System.out.println(systemipaddress);
        return systemipaddress;
	}

}
