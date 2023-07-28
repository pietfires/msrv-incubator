package com.incubator.vrsa.service;

import com.incubator.vrsa.dtos.MovieDto;
import com.incubator.vrsa.models.ImdbMovieResponse;
import org.springframework.stereotype.Service;

@Service
public class MovieMapper {
    public MovieDto mapMovie(ImdbMovieResponse imdbMovieResponse) {
        MovieDto responseDto = new MovieDto();
        responseDto.setId(imdbMovieResponse.getId());
        responseDto.setRating(imdbMovieResponse.getMovieRating());
        return responseDto;
    }
}
