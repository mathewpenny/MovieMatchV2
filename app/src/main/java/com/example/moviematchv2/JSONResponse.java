package com.example.moviematchv2;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONResponse {
    @SerializedName("results")
    @Expose
    private Movie[] movieList;

    public Movie[] getMovieList() {
        return movieList;
    }
}

