package com.incubator.vrsa.controller;

import com.incubator.vrsa.dtos.MovieDto;
import com.incubator.vrsa.models.ImdbMovieResponse;
import com.incubator.vrsa.models.MovieDetailResponse;
import com.incubator.vrsa.service.MovieMapper;
import com.incubator.vrsa.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class Movies {

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private MovieService movieService;

    @Operation(summary = "Get top 10 movies from IMDb API")
    @GetMapping("/top-movies")
    public ResponseEntity<List<MovieDto>> getTopMovies() {
        ImdbMovieResponse[] imdbResponse = movieService.getTopTenMovies();
        List<MovieDto> movies = new ArrayList<>();
        // map each movie to the specified MovieDto and ensure it only does it for 10 movies
        for (int i =0; i< 10 && i < imdbResponse.length; i++){
            MovieDetailResponse mdr = movieService.getMoviePlot(imdbResponse[i].getId());
            movies.add(movieMapper.mapMovie(imdbResponse[i], mdr.getDescription()));
        }
        return  ResponseEntity.ok(movies);
    }
}
