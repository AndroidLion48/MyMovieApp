package com.eaglewarrior.movieapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.eaglewarrior.movieapp.databinding.ActivityMovieDetailsBinding;
import com.eaglewarrior.movieapp.models.MovieModel;

import androidx.appcompat.app.AppCompatActivity;

public class MovieDetails extends AppCompatActivity {

    ActivityMovieDetailsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(LayoutInflater.from(this));
        View bindingRoot = binding.getRoot();
        setContentView(binding.getRoot());

        GetDataFromIntent();

    }

    private void GetDataFromIntent() {

        if (getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            Log.v("Tagy", "incoming intent" + movieModel.getId());

            binding.tvTitleDetails.setText(movieModel.getTitle());
            binding.tvDescDetails.setText(movieModel.getOverview());
            binding.ratingBarDetails.setRating(movieModel.getVote_average());


            Log.v("Tagy", movieModel.getOverview());
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path()).into(binding.imageViewDetails);

        }

    }


}
