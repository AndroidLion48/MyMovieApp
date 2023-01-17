package com.eaglewarrior.movieapp.request;

import android.util.Log;

import com.eaglewarrior.movieapp.AppExecutors;
import com.eaglewarrior.movieapp.models.MovieModel;
import com.eaglewarrior.movieapp.response.MovieSearchResponse;
import com.eaglewarrior.movieapp.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Clarence E Moore on 2023-01-11.
 * <p>
 * Description:
 */

public class TMDBApiClient {

    // LiveData for search
    private MutableLiveData<List<MovieModel>> mMovies;
    public LiveData<List<MovieModel>> getMovies() {return mMovies;}
    // Live Data for popular movies
    private MutableLiveData<List<MovieModel>> popularMovies;
    public LiveData<List<MovieModel>> getPopularMovies() {return popularMovies;}

    private static TMDBApiClient instance;

    // Making Global RUNNABLE
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    // making Popular runnable
    private RetrievePopularMoviesRunnable retrievePopularMoviesRunnable;


    public static TMDBApiClient getInstance() {

        if (instance == null) {
            instance = new TMDBApiClient();
        }
        return instance;
    }

    private TMDBApiClient() {

        mMovies = new MutableLiveData<>();
        popularMovies = new MutableLiveData<>();
    }

    // This Method will be called through the classes
    public void getMoviesByQuery(String query, int pageNumber) {

        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future handler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                handler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    public void getPopularMovies(int pageNumber) {

        if (retrievePopularMoviesRunnable != null) {
            retrievePopularMoviesRunnable = null;
        }

        retrievePopularMoviesRunnable = new RetrievePopularMoviesRunnable(pageNumber);

        final Future handler = AppExecutors.getInstance().networkIO().submit(retrievePopularMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                handler.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }



    // Retrieving data from RestAPI by runnable class
    // We hve two queries search by Movie_ID and search by Movie
    private class RetrieveMoviesRunnable implements Runnable {

        private final String query;
        private final int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {

            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            // Getting the response objects
            try {
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        // Sending data to live data
                        // PostValue: used for background thread
                        // setValue: NOT for background thread
                        mMovies.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e("Tag", "Error Movies " + error);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }

        //Search Method/ query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {

            return TMDBservice.getMovieApi().searchMovie(Credentials.API_KEY, query, pageNumber);
        }

        private void cancelRequest() {

            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;

        }
    }

    private class RetrievePopularMoviesRunnable implements Runnable {


        private int pageNumber;
        boolean cancelRequest;

        public RetrievePopularMoviesRunnable(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            // Getting the response objects
            try {
                Response response = getPop(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        // Sending data to live data
                        // PostValue: used for background thread
                        // setValue: NOT for background thread
                        popularMovies.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = popularMovies.getValue();
                        currentMovies.addAll(list);
                        popularMovies.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e("Tag", "Error Popular " + error);
                    popularMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                popularMovies.postValue(null);
            }
        }

        //Search Method/ query
        private Call<MovieSearchResponse> getPop(int pageNumber) {

            return TMDBservice.getMovieApi().getPopular(Credentials.API_KEY, pageNumber) ;
        }

        private void cancelRequest() {

            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;

        }
    }

}
