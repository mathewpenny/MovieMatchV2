package com.example.moviematchv2;

public class Movie {

    private String title;
    private String tmdbID;
    private StreamingInfo streamingInfo;
    private PosterURLs posterURLs;
    private int year;
    private int runtime;
    private String overview;
    private String originalLanguage;

    public Movie() {
    }

    public Movie(String title, String tmdbID) {
        this.title = title;
        this.tmdbID = tmdbID;
    }

    public Movie(String title, String tmdbID, int year, int runtime, String overview, PosterURLs posterURLS, String originalLanguage, StreamingInfo streamingInfo) {
        this.title = title;
        this.tmdbID = tmdbID;
        this.year = year;
        this.runtime = runtime;
        this.overview = overview;
        this.posterURLs = posterURLS;
        this.originalLanguage = originalLanguage;
        this.streamingInfo = streamingInfo;
    }


    public StreamingInfo getStreamingInfo() {
        return streamingInfo;
    }

    public void setStreamingInfo(StreamingInfo streamingInfo) {
        this.streamingInfo = streamingInfo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
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
