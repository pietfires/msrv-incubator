package com.incubator.vrsa.service;

import com.incubator.vrsa.dtos.MovieDto;
import com.incubator.vrsa.models.ImdbMovieResponse;
import org.springframework.stereotype.Service;

@Service
public class MovieMapper {
    public MovieDto mapMovie(ImdbMovieResponse imdbMovieResponse, String description) {
        MovieDto responseDto = new MovieDto();
        responseDto.setTitle(imdbMovieResponse.getTitle());
        responseDto.setImageUrl(imdbMovieResponse.getImage());
        responseDto.setId(imdbMovieResponse.getId());
        responseDto.setPlot(description);
        return responseDto;
    }
}
