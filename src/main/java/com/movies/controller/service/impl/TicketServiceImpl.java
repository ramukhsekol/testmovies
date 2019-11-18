package com.movies.controller.service.impl;

import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Override
	public HttpResponse<String> getMovieTricket(String movieId, String ipAddress) throws UnirestException, UnknownHostException {
		/*
		 * String ipAddress = IpAddressUtil.getPublicIpAddress();
		 * if(StringUtils.hasText(ipAddress) && ipAddress.contains("html")) { ipAddress
		 * = IpAddressUtil.getPublicIpAddress(); }
		 */
		HttpResponse<String> response = Unirest.get("https://videospider.in/getticket.php?key=5HbImlTRhrrI7aEO&secret_key=s51qflw236t2ddlcbom9d0hfe800tp&video_id="+movieId+"&ip="+ipAddress).asString();
		System.out.println(response);
		return response;
	}

}
