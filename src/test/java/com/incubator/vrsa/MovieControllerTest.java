package com.incubator.vrsa;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.incubator.vrsa.controller.MoviesController;
import com.incubator.vrsa.dtos.MovieDto;
import com.incubator.vrsa.models.ImdbMovieResponse;
import com.incubator.vrsa.models.MovieDetailResponse;
import com.incubator.vrsa.service.MovieMapper;
import com.incubator.vrsa.service.MovieService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieControllerTest {

    private static final int WIREMOCK_PORT = 8085;
    private static WireMockServer wireMockServer;
    @InjectMocks
    private MoviesController movieController;
    @Mock
    private MovieService movieService;
    @Mock
    private MovieMapper movieMapper;
    private ImdbMovieResponse[] imdbMovieResponses;

    @BeforeClass
    public static void setupServer() {
        wireMockServer = new WireMockServer(WIREMOCK_PORT);
        wireMockServer.start();
        WireMock.configureFor("localhost", WIREMOCK_PORT);
    }

    @AfterClass
    public static void stopServer() {
        wireMockServer.stop();
    }

    @Before
    public void setup() {
        imdbMovieResponses = new ImdbMovieResponse[10];
        for (int i = 0; i < 10; i++) {
            imdbMovieResponses[i] = new ImdbMovieResponse();
            imdbMovieResponses[i].setId("id" + i);
            imdbMovieResponses[i].setTitle("title" + i);
            imdbMovieResponses[i].setRank(i);
            imdbMovieResponses[i].setImage("image" + i);
            imdbMovieResponses[i].setPlot("plot" + i);
        }
    }

    @Test
    public void testGetTopMoviesWithoutFilter() throws Exception {
        when(movieService.getTopTenMovies()).thenReturn(imdbMovieResponses);

        for (int i = 0; i < 10; i++) {
            MovieDetailResponse movieDetailResponse = new MovieDetailResponse();
            movieDetailResponse.setDescription("description" + i);
            movieDetailResponse.setId("id" + i);
            when(movieService.getMoviePlot("id" + i)).thenReturn(movieDetailResponse);
            MovieDto movieDto = new MovieDto();
            movieDto.setId("id" + i);
            movieDto.setPlot("plot" + i);
            movieDto.setImageUrl("image" + i);
            movieDto.setTitle("title" + i);
            when(movieMapper.mapMovie(any(ImdbMovieResponse.class), anyString())).thenReturn(movieDto);
        }

        ResponseEntity<List<MovieDto>> responseEntity = movieController.getTopMovies(null);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<MovieDto> movies = responseEntity.getBody();
        Assert.assertEquals(10, movies.size());
    }

    @Test
    public void testGetTopMoviesWithFilter() throws Exception {
        when(movieService.getTopTenMovies()).thenReturn(imdbMovieResponses);

        for (int i = 0; i < 10; i++) {
            MovieDetailResponse movieDetailResponse = new MovieDetailResponse();
            movieDetailResponse.setDescription("description" + i);
            movieDetailResponse.setId("id" + i);
            when(movieService.getMoviePlot("id" + i)).thenReturn(movieDetailResponse);

            when(movieMapper.mapMovie(any(ImdbMovieResponse.class), anyString())).thenAnswer(invocation -> {
                ImdbMovieResponse response = (ImdbMovieResponse) invocation.getArguments()[0];
                String plot = (String) invocation.getArguments()[1];

                MovieDto movieDto = new MovieDto();
                movieDto.setId(response.getId());
                movieDto.setPlot(plot);
                movieDto.setImageUrl(response.getImage());
                movieDto.setTitle(response.getTitle());

                return movieDto;
            });

        }

        ResponseEntity<List<MovieDto>> responseEntity = movieController.getTopMovies("title1");
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<MovieDto> movies = responseEntity.getBody();
        Assert.assertEquals(1, movies.size());
    }
}
