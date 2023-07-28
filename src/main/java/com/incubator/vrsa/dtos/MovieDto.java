package com.incubator.vrsa.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieDto {
   @JsonProperty("id")
    private String id;
   @JsonProperty("rating")
    private Double rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
