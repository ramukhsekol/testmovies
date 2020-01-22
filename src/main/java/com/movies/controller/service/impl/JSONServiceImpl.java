package com.movies.controller.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.controller.service.JSONService;
import com.movies.controller.service.UploadPathService;
import com.movies.dto.Movie;
import com.movies.dto.MovieLinks;



@Service
public class JSONServiceImpl implements JSONService {
	
	@Autowired private UploadPathService uploadPathService;

	@Override
	public List<Movie> getMovies(int sindex) {
		try {
			List<Movie> movies = new ArrayList<Movie>();
			Document doc = Jsoup.connect("https://3movierulz.vc/category/telugu-movie/page/" + sindex).timeout(10000).validateTLSCertificates(false).get();
			Element body =  doc.body();
			Elements elements = body.getElementsByClass("cont_display");
			int index = 1;
			for(Element element : elements) {
				Element link = element.select("a").first();
				String linkHref = link.attr("href");
				Document document = Jsoup.connect(linkHref).timeout(10000).validateTLSCertificates(false).get();
				Element documentbody =  document.body();
				Elements elements2 = documentbody.getElementsByClass("entry-content");
				Element movieimage = elements2.select("img").first();
				String image = movieimage.absUrl("src");
				Movie movie = new Movie();
				movie.setId((long)index);
				movie.setMovieImage(image);
				Elements paragraphs = elements2.select("p");
				int i = 1;
				List<MovieLinks> links = new ArrayList<MovieLinks>();
				for(Element para : paragraphs) {
					if(i == 1) {
						String movieName = para.text().split("\\(")[0];
						String dMovieName = movieName.replace("Watch ", "").trim();
						System.out.println(dMovieName);
						movie.setMovieName(dMovieName);
						if(!movieName.trim().contains("I") && 
								!movieName.trim().contains("Dhanalakshmi Talupu Tadithey") && 
								!movieName.trim().contains("Mental") && 
								!movieName.trim().contains("Aata Aarambam") && 
								!movieName.trim().contains("Rajadhi Raja") && 
								!movieName.trim().contains("S/O Satyamurthy") && 
								!movieName.trim().contains("Watch Yentavaadu Gaani") && 
								!movieName.trim().contains("Lacchimdeviki O Lekkundi") && 
								!movieName.trim().contains("Control C") && 
								!movieName.trim().contains("Kadambari") && 
								!movieName.trim().contains("Meelo Evaru Koteeswarudu") && 
								!movieName.trim().contains("Balam")) {
							Integer movieYear = Integer.parseInt(para.text().substring(para.text().indexOf("(")+1,para.text().indexOf(")")));
							System.out.println(movieYear);
							movie.setMovieYear(movieYear);
						}
						
					} else if(i == 2) {
						movie.setMovieInfo(para.html());
					} else if(i == 3) {
						movie.setMovieDescription(para.text());
					}
					
					Element p = para.select("a").first();
					if(p!=null) {
						String q = p.attr("href");
						if(q!=null && q.startsWith("http")) {
							MovieLinks movieLinks = new MovieLinks();
							movieLinks.setMovieLink(q);
							links.add(movieLinks);
						}
					}
					i++;
				}
				movie.setMovieLinks(links);
				movies.add(movie);
				index++;
			}
			System.out.println(movies.size());
			return movies;
			
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public boolean generateJsonFile(int index, List<Movie> movies) {
		File file = uploadPathService.getFilePath(index + ".json", "movies");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(file, movies);
			return true;
			
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Movie> getMoviesByIndex(String pageIndex) {
		File file = uploadPathService.getFilePath(pageIndex + ".json", "movies");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<Movie> movies = Arrays.asList(objectMapper.readValue(file, Movie[].class));
			return movies;
		} catch (IOException e) {
		}
		return null;
	}

}
