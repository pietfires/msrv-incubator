package com.incubator.vrsa.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.incubator.vrsa.dtos.MovieDto;
import com.incubator.vrsa.exceptions.MovieServiceException;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Tag(name = "Movie Controller", description = "Returns all movie related endpoints")
@RestController
@RequestMapping("/api/v1/movies")
public class MoviesController {

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private MovieService movieService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Operation(summary = "Get top 10 movies from IMDb API")
    @GetMapping("/top-movies")

    public ResponseEntity<List<MovieDto>> getTopMovies(@RequestParam(required = false) String titleSearch) throws MovieServiceException {

        ImdbMovieResponse[] imdbResponse = movieService.getTopTenMovies();
        List<Future<MovieDto>> futures = new ArrayList<>();
        for (int i = 0; i < 10 && i < imdbResponse.length; i++) {
            final int index = i;
            Future<MovieDto> future = executorService.submit(() -> {
                MovieDetailResponse mdr = movieService.getMoviePlot(imdbResponse[index].getId());
                return movieMapper.mapMovie(imdbResponse[index], mdr.getDescription());
            });
            futures.add(future);
        }

        List<MovieDto> movies = new ArrayList<>();
        for (Future<MovieDto> future : futures) {
            try {
                MovieDto movieDto = future.get();
                movies.add(movieDto);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                throw new MovieServiceException("Unable to get movie list from server", e.getCause());
            }
        }
        if (titleSearch == null) {
            return ResponseEntity.ok(movies);
        } else {
            List<MovieDto> filteredMovieList = movies.stream()
                    .filter(p -> p.getTitle() != null && p.getTitle().toLowerCase().contains(titleSearch.toLowerCase()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(filteredMovieList);
        }
    }
}
