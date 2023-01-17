package com.eaglewarrior.movieapp.adapters;

import android.view.View;

import com.eaglewarrior.movieapp.databinding.PopularMoviesLayoutBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularViewHolder extends RecyclerView.ViewHolder implements View .OnClickListener{

    PopularMoviesLayoutBinding binding;
    OnMovieListener onMovieListener;


    public PopularViewHolder(@NonNull PopularMoviesLayoutBinding itembinding, OnMovieListener onMovieListener) {
        super(itembinding.getRoot());
        binding = itembinding;

        this.onMovieListener = onMovieListener;

        itembinding.getRoot().setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        // Display details
    }
}
