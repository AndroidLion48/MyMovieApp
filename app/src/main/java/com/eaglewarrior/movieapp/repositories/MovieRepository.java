package com.eaglewarrior.movieapp.repositories;

import com.eaglewarrior.movieapp.models.MovieModel;
import com.eaglewarrior.movieapp.request.TMDBApiClient;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Created by Clarence E Moore on 2023-01-11.
 * <p>
 * Description:
 */

public class MovieRepository {
    // This class is for repositories


    private static MovieRepository instance;

    private TMDBApiClient TMDBApiClient;

    private String mQuery;
    private int mPageNumber;


    public static MovieRepository getInstance() {

        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {

        TMDBApiClient = TMDBApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {return TMDBApiClient.getMovies();}
    public LiveData<List<MovieModel>> getPop() {return TMDBApiClient.getPopularMovies();}




    // Calling the method in repository
    public void getMoviesByQuery(String query, int pageNumber){

        mQuery = query;
        mPageNumber = pageNumber;
        TMDBApiClient.getMoviesByQuery(query, pageNumber);
    }

    public void searchMoviePop(int pageNumber){

        mPageNumber = pageNumber;
        TMDBApiClient.getPopularMovies(pageNumber);
    }


    public void searchNextPage(){
        getMoviesByQuery(mQuery, mPageNumber + 1);

    }
}
