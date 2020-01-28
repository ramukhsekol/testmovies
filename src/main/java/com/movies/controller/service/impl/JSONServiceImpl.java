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
import com.movies.dto.Alphamovies;
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
			int movieindex = 1;
			for(Element element : elements) {
				if(movieindex<=7) {
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
					movieindex++;
				}
				
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

	@Override
	public List<Alphamovies> getAplhaMovies(int sindex) {
		try {
			List<Alphamovies> movies = new ArrayList<Alphamovies>();
			Document doc = Jsoup.connect("https://ww.0123movies.su/library/U/page/"+sindex+"/").timeout(10000).validateTLSCertificates(false).get();
			Element body =  doc.body();
			Elements elements = body.getElementsByClass("mlnh-thumb");
			int index = 1;
			for(Element element : elements) {
				Alphamovies apAlphamovies = new Alphamovies();
					apAlphamovies.setId((long)index);
				Element link = element.select("a").first();
				String linkHref = link.attr("href");
				Document document = Jsoup.connect(linkHref).timeout(10000).validateTLSCertificates(false).get();
				Element documentbody =  document.body();
				Element pelement = documentbody.getElementById("mv-info");
				Element plink = pelement.select("a").first();
				String plinkHref = plink.attr("href");
				Document cdocument = Jsoup.connect(plinkHref).timeout(10000).validateTLSCertificates(false).get();
				Element cdocumentbody =  cdocument.body();
				Element pcelement = cdocumentbody.getElementById("server-1");
				if(pcelement!=null) {
					Element pclink = pcelement.select("a").first();
					String pclinkHref = pclink.attr("data-svv1");
						apAlphamovies.setLink("https://vidcloud9.com/streaming.php?"+pclinkHref);
					Elements imageLink = cdocumentbody.getElementsByClass("mvic-thumb");
					Element movieimage = imageLink.select("img").first();
					String image = movieimage.absUrl("src");
						apAlphamovies.setImage(image);
					Elements tiltleLink = cdocumentbody.getElementsByClass("mvic-desc");
					Element movietitle = tiltleLink.select("h3").first();
						apAlphamovies.setName(movietitle.text());
					Elements descLink = cdocumentbody.getElementsByClass("desc");
					String moviedesc = descLink.text();
						apAlphamovies.setDescription(moviedesc);
					Elements infoLink = cdocumentbody.getElementsByClass("mvic-info");
					Elements dataLink = infoLink.select("p");
					System.out.println(movietitle.text()+" "+pclinkHref+" "+image+" "+moviedesc);
					for(Element info : dataLink) {
						String data = info.text();
						if(data!=null) {
							String[] spilt = data.split(":");
							if(spilt!=null && spilt.length>1) {
								if(spilt[0].equalsIgnoreCase("Genre")) {
									String genre = spilt[1]!=null?spilt[1].trim():null;
									apAlphamovies.setGenre(genre);
								} else if(spilt[0].equalsIgnoreCase("Director")) {
									String director = spilt[1]!=null?spilt[1].trim():null;
									apAlphamovies.setDirector(director);
								} else if(spilt[0].equalsIgnoreCase("Country")) {
									String country = spilt[1]!=null?spilt[1].trim():null;
									apAlphamovies.setCountry(country);
								} else if(spilt[0].equalsIgnoreCase("Duration")) {
									String duration = spilt[1]!=null?spilt[1].trim():null;
									apAlphamovies.setDuration(duration);
								} else if(spilt[0].equalsIgnoreCase("Quality")) {
									String quality = spilt[1]!=null?spilt[1].trim():null;
									apAlphamovies.setQuality(quality);
								} else if(spilt[0].equalsIgnoreCase("Release")) {
									String year = spilt[1]!=null?spilt[1].trim():null;
									Integer iYear = (year!=null && year!="")?Integer.parseInt(year):0;
									apAlphamovies.setYear(iYear);
								} else if(spilt[0].equalsIgnoreCase("IMDb")) {
									String rating = spilt[1]!=null?spilt[1].trim():null;
									apAlphamovies.setRating(rating);
								} else if(spilt[0].equalsIgnoreCase("Writer")) {
									String writer = spilt[1]!=null?spilt[1].trim():null;
									apAlphamovies.setWriter(writer);
								}
							}
						}
					}
				}
				movies.add(apAlphamovies);
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
	public boolean generateAlphaJsonFile(int index, List<Alphamovies> movies) {
		File file = uploadPathService.getFilePath(index + ".json", "alpha/u");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(file, movies);
			return true;
			
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Alphamovies> getMoviesByIndex(String movietype, String pageIndex) {
		File file = uploadPathService.getFilePath(pageIndex + ".json", "alpha/"+movietype.toLowerCase());
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<Alphamovies> movies = Arrays.asList(objectMapper.readValue(file, Alphamovies[].class));
			return movies;
		} catch (IOException e) {
		}
		return null;
	}

}
