package com.example.moviematchv2;

public class Movie {

    private String title, tmdbID;
    private PosterURLs posterURLs;


    public Movie(String title, String tmdbID, PosterURLs posterURLS) {
        this.title = title;
        this.tmdbID = tmdbID;
        this.posterURLs = posterURLS;
    }

    public Movie() {
    }

    public String getTmdbID() {
        return tmdbID;
    }

    public void setTmdbID(String tmdbID) {
        this.tmdbID = tmdbID;
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
