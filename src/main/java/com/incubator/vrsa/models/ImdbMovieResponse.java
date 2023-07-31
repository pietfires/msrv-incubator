package com.incubator.vrsa.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImdbMovieResponse implements Serializable {

    @JsonProperty("rank")
    private int rank;
    @JsonProperty("title")
    private String title;
    @JsonProperty("image")
    private String image;
    @JsonProperty("id")
    private String id;

    @JsonProperty("thumbnail")
    private String plot;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
