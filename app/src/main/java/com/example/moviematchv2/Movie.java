package com.example.moviematchv2;

public class Movie {

    private String title;
    private PosterURLs posterURLs;


    public Movie(String title, PosterURLs posterURLS) {
        this.title = title;
        this.posterURLs = posterURLS;
    }

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PosterURLs getPosterURLs() {
        return posterURLs;
    }

    public void setPosterURLs(PosterURLs posterURLs) {
        this.posterURLs = posterURLs;
    }
}
