package com.incubator.vrsa.service;

import com.incubator.vrsa.models.Movie;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestMovieService {
    private final RestTemplate restTemplate;

    public RestMovieService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Movie[] getListOfMovies() {
        String url = ""; // add imdb list - probably going to have to use a param to get some sort of list
        return this.restTemplate.getForObject(url, Movie[].class);
    }
}
