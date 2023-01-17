package com.eaglewarrior.movieapp.response;

import com.eaglewarrior.movieapp.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

/**
 * Created by Clarence E Moore on 2023-01-06.
 * <p>
 * Description:
 */
// This class is for single movie request
class MovieResponse {
    // 1 Find the Movie Object

    @SerializedName("results")
    @Expose
    private MovieModel movie;
    public MovieModel getMovie() {
        return movie;

    }

    @NonNull
    @Override
    public String toString() {
        return "MovieResponse{" + "movie=" + movie + '}';

    }
}
