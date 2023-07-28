package com.incubator.vrsa.service;

import com.incubator.vrsa.exceptions.ApiException;
import com.incubator.vrsa.models.ImdbMovieResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    private final String URL = "https://imdb8.p.rapidapi.com/title/get-top-rated-movies";
    private final RestTemplate restTemplate;

    public MovieService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    public ImdbMovieResponse[] getTopTenMovies() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Host", "imdb8.p.rapidapi.com");
            headers.set("X-RapidAPI-Key", "");
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
}
