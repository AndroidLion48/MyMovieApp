package com.eaglewarrior.movieapp.adapters;

import android.view.View;

import com.eaglewarrior.movieapp.databinding.MovieListItemBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Clarence E Moore on 2023-01-11.
 * <p>
 * Description:
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    MovieListItemBinding binding;

    // Click Listener
    OnMovieListener onMovieListener;


    public MovieViewHolder(@NonNull MovieListItemBinding itemBinding, OnMovieListener onMovieListener) {
        super(itemBinding.getRoot());
        binding = itemBinding;

        this.onMovieListener = onMovieListener;

        itemBinding.getRoot().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
