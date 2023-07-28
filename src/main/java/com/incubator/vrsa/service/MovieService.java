package com.incubator.vrsa.service;

import com.incubator.vrsa.exceptions.ApiException;
import com.incubator.vrsa.models.ImdbMovieResponse;
import com.incubator.vrsa.models.MovieDetailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {
    @Value("${imdb.url}")
    private String URL;
    @Value("${rapidapi.apikey}")
    private String API_KEY;
    private final RestTemplate restTemplate;

    public MovieService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    public ImdbMovieResponse[] getTopTenMovies() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Host", "imdb-top-100-movies.p.rapidapi.com");
            headers.set("X-RapidAPI-Key", API_KEY);
            HttpEntity<Void> entity = new HttpEntity<>(headers); // No request body for a GET request

            ResponseEntity<ImdbMovieResponse[]> response = restTemplate.exchange(URL, HttpMethod.GET, entity, ImdbMovieResponse[].class);
            if(response.getStatusCode() == HttpStatus.OK){
                return response.getBody();
            } else {
                throw new ApiException("Error fetching movies. HTTP status: " + response.getStatusCode());
            }
        }catch(RestClientException e){
            throw new ApiException("Error while fetching movies: " + e.getMessage());
        }
    }
    public MovieDetailResponse getMoviePlot(String id){

        try{
            String detailsURL = String.format("https://imdb-top-100-movies.p.rapidapi.com/%s", id);
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Host", "imdb-top-100-movies.p.rapidapi.com");
            headers.set("X-RapidAPI-Key", API_KEY);
            HttpEntity<Void> entity = new HttpEntity<>(headers); // No request body for a GET request

            ResponseEntity<MovieDetailResponse> response = restTemplate.exchange(detailsURL, HttpMethod.GET, entity, MovieDetailResponse.class);
            if(response.getStatusCode() == HttpStatus.OK){
                return response.getBody();
            } else {
                throw new ApiException("Error fetching movies details. HTTP status: " + response.getStatusCode());
            }
        }catch(RestClientException e){
            throw new ApiException("Error fetching movie details: "+ e.getMessage());
        }
    }
}
