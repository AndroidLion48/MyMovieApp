package com.eaglewarrior.movieapp.utils;

import com.eaglewarrior.movieapp.models.MovieModel;
import com.eaglewarrior.movieapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Clarence E Moore on 2023-01-05.
 * <p>
 * Description:
 */

public interface MovieApi {

    // Search for Movies

    /**
     * When making Calls to an Api remember REMEMBER
     */
    //https://api.themoviedb.org/3/search/movie?api_key=ebc652e6e92d8f96fa82a1199965bafe&query=Jack+Reacher
    @GET("search/movie?")
    Call<MovieSearchResponse> searchMovie(@Query("api_key") String key, @Query("query") String query, @Query("page") int page);

    // Search by ID
    // Movie ID 550 for Jack Reacher
    @GET("movie/{movie_id}?")
    Call<MovieModel> getMovie(@Path("movie_id") int movie_id, @Query("api_key") String api_key
                             );

    // Getting Popular Movies
    //https://api.themoviedb.org/3/movie/popular api_key=ebc652e6e92d8f96fa82a1199965bafe&page=1
    @GET("movie/popular?")
    Call<MovieSearchResponse> getPopular(@Query("api_key") String Key, @Query("page") int page
                                        );


}
