package com.eaglewarrior.movieapp.adapters;

/**
 * Created by Clarence E Moore on 2023-01-11.
 * <p>
 * Description:
 */

public interface OnMovieListener {

    void onMovieClick(int position);

    void onCategoryClick(String category);
}
