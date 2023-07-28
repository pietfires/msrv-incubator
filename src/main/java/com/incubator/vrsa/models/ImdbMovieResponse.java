package com.incubator.vrsa.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImdbMovieResponse implements Serializable {

    @JsonProperty("id")
    private String id;
    @JsonProperty("chartRating")
    private Double movieRating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(Double movieRating) {
        this.movieRating = movieRating;
    }
}
