package com.eaglewarrior.movieapp.response;

import com.eaglewarrior.movieapp.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Clarence E Moore on 2023-01-06.
 * <p>
 * Description:
 */
// This class is for getting multiple movies (A Movies list) - Popular movies
public class MovieSearchResponse {

    @SerializedName("total_results")
    @Expose()

    private int total_count;

    @SerializedName("results")
    @Expose

    private List<MovieModel> movies;

    public int getTotal_count() {
        return total_count;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    @NonNull
    @Override
    public String toString() {

        return "MovieSearchResponse{" + "total_count=" + total_count + ", movies=" + movies + '}';
    }
}
