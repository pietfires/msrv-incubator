package com.incubator.vrsa.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.incubator.vrsa.dtos.MovieDto;
import com.incubator.vrsa.models.ImdbMovieResponse;
import com.incubator.vrsa.models.MovieDetailResponse;
import com.incubator.vrsa.service.MovieMapper;
import com.incubator.vrsa.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Movie Controller", description = "Returns all movie related endpoints")
@RestController
@RequestMapping("/api/v1/movies")
public class MoviesController {

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private MovieService movieService;

    @Operation(summary = "Get top 10 movies from IMDb API")
    @GetMapping("/top-movies")

    public ResponseEntity<List<MovieDto>> getTopMovies(@RequestParam(value = "default", required = false) String titleSearch) {

        ImdbMovieResponse[] imdbResponse = movieService.getTopTenMovies();
        List<MovieDto> movies = new ArrayList<>();
        // map each movie to the specified MovieDto and ensure it only does it for 10 movies
        for (int i = 0; i < 10 && i < imdbResponse.length; i++) {
            MovieDetailResponse mdr = movieService.getMoviePlot(imdbResponse[i].getId());
            movies.add(movieMapper.mapMovie(imdbResponse[i], mdr.getDescription()));
        }
        if (titleSearch == null || titleSearch.equals("default")) {
            return ResponseEntity.ok(movies);
        } else {
            List<MovieDto> filteredMovieList = movies.stream()
                    .filter(p -> p.getTitle() != null && p.getTitle().toLowerCase().contains(titleSearch.toLowerCase()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(filteredMovieList);
        }
    }
}
