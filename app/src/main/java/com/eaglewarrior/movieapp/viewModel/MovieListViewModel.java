package com.eaglewarrior.movieapp.viewModel;

import com.eaglewarrior.movieapp.models.MovieModel;
import com.eaglewarrior.movieapp.repositories.MovieRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Clarence E Moore on 2023-01-11.
 * <p>
 * Description:
 */

public class MovieListViewModel extends ViewModel {
    // this class is used for VIEWMODEL

    private MovieRepository movieRepository;


    // Constructor
    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();

    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
        }

    public LiveData<List<MovieModel>> getPop(){
        return movieRepository.getPop();
    }

    // Calling method in ViewModel
    public void getMoviesByQuery(String query, int pageNumber) {
        movieRepository.getMoviesByQuery(query, pageNumber);
    }
    public void searchMoviePop(int pageNumber) {
        movieRepository.searchMoviePop(pageNumber);
    }


    public void searchNextPage(){
        movieRepository.searchNextPage();
    }

}
