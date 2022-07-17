package com.example.moviematchv2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MovieApi {

    @Headers("X-RapidAPI-Key: 773f617c47mshddd96ac84298570p1ff3f7jsnacc2ecf64d46")
    @GET("basic?country=ca&type=movie")
    Call<JSONResponse> getMovies(@Query("page") Integer page,
                                 @Query("service") String service,
                                 @Query("genre") Integer genre); //New added genres
}
