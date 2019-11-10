package com.movies.controller;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movies.controller.service.PopcronService;

@Controller
public class PopcronController {
	
	@Autowired private PopcronService popcronService;
	
	@GetMapping("/")
	public String getmovierooms(Model model) throws UnirestException{
		HttpResponse<String> response = popcronService.getmovierooms();
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<String>>() {}.getType();
		List<String> movierooms = gson.fromJson(response.getBody(), type);
		
		model.addAttribute("movierooms", movierooms);
		return "view/movierooms";
		
	}

}
