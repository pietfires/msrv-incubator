package com.incubator.vrsa.models;

import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private String paragraphText;
    private String imageUrl;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParagraphText(String paragraphText) {
        this.paragraphText = paragraphText;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getParagraphText() {
        return paragraphText;
    }

    public String getTitle() {
        return title;
    }
}
