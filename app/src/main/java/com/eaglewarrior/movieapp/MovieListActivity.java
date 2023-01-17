package com.eaglewarrior.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.eaglewarrior.movieapp.adapters.MovieAdapter;
import com.eaglewarrior.movieapp.adapters.OnMovieListener;
import com.eaglewarrior.movieapp.databinding.ActivityMainBinding;
import com.eaglewarrior.movieapp.models.MovieModel;
import com.eaglewarrior.movieapp.viewModel.MovieListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    ActivityMainBinding binding;

    private MovieAdapter movieAdapter;

    // ViewModel
    private MovieListViewModel movieListViewModel;


    boolean isPopular = true;


    // Adding Network Security Configuration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));

        setContentView(binding.getRoot());

        // Toolbar
        setSupportActionBar(binding.toolbar);

        // SearchView
        SearchViewSetup();

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);


        ConfigureRecyclerView();
        ObserveAnyChange();
        ObservePopularMovies();

        // Getting popular M
        movieListViewModel.searchMoviePop(1);
    }

    private void ObservePopularMovies(){
        movieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing any data change
                if (movieModels != null) {
                    movieAdapter.setmMovies(movieModels);

                }
            }
        });
    }

    // Observing any data change
    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing any data change
                if (movieModels != null) {
                    movieAdapter.setmMovies(movieModels);
                }
            }
        });
    }

    // Initializing recyclerView & add data
    private void ConfigureRecyclerView(){
        // Live Data can't be passed via the constructor
        movieAdapter = new MovieAdapter(this);

        binding.recyclerView.setAdapter(movieAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // RecyclerView Pagination
        // Loading next page of api response
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!binding.recyclerView.canScrollVertically(1)){
                    // Here we will display the next search results on the next page of api
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }

    @Override
    public void onMovieClick(int position) {

//        Toast.makeText(this, "The Position" + position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieAdapter.getSelectedMovie(position));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(String category) {

    }

    // Get data from searchview & query the api to get results
    private void SearchViewSetup() {

        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.getMoviesByQuery(
                        query,
                        1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                isPopular = false;
            }
        });

    }

    //    private void GetRetrofitResponse() {
//
//        MovieApi movieApi = Servicey.getMovieApi();
//        Call<MovieSearchResponse> responseCall = movieApi
//                .searchMovie(
//                        Credentials.API_KEY,
//
//                        "Fight Club",
//                        1);
//
//        responseCall.enqueue((new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                if(response.code() == 200) {
//                    assert response.body() != null;
//                    Log.v("Tag", "the response" + response.body().toString());
//
//                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
//
//                    for(MovieModel movie: movies) {
//                        Log.v("Tag","Name: " + movie.getMovie_id());
//                    }
//                } else {
//
//                    try {
//                        assert response.errorBody() != null;
//                        Log.v("Tag", "Error" + response.errorBody().toString());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//                t.printStackTrace();
//
//            }
//        }));
//    }
//
//    private void GetRetrofitResponseAccordingToID() {
//
//        MovieApi movieApi = Servicey.getMovieApi();
//        Call<MovieModel> responseCall = movieApi
//                .getMovie(550,
//                          Credentials.API_KEY);
//
//        responseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if (response.code() == 200){
//                    MovieModel movieModel = response.body();
//                    Log.v("Tag", "The Response " + movieModel.getTitle());
//                } else {
//                    try {
//                        Log.v("Tag", "Error " + response.errorBody());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
}
