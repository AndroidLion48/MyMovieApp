package com.eaglewarrior.movieapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.eaglewarrior.movieapp.databinding.MovieListItemBinding;
import com.eaglewarrior.movieapp.databinding.PopularMoviesLayoutBinding;
import com.eaglewarrior.movieapp.models.MovieModel;
import com.eaglewarrior.movieapp.utils.Credentials;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Clarence E Moore on 2023-01-11.
 * <p>
 * Description:
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    private static final int DISPLAY_POP = 1;
    private static final int DISPLAY_SEARCH = 2;

    public MovieAdapter(OnMovieListener onMovieListener) {

        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ListType.SEARCH.getViewType()) {
            MovieListItemBinding binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

            return new MovieViewHolder(binding, onMovieListener);
        } else {
            PopularMoviesLayoutBinding binding = PopularMoviesLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

            return new PopularViewHolder(binding, onMovieListener);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        int itemViewType = getItemViewType(i);
        if (itemViewType == ListType.SEARCH.getViewType()) {

            // vote average is 10, and our rating bar is 5 stars: dividing by 2
            ((MovieViewHolder) holder).binding.ratingBar.setRating((mMovies.get(i).getVote_average()) / 2);

            if (mMovies.get(i).getPoster_path() != null) {
                // ImageView: Using Glide Library
                Glide.with(((MovieViewHolder) holder).binding.getRoot().getContext()).load(Credentials.BASE_POSTER_URL + mMovies.get(i).getPoster_path())
                     .into(((MovieViewHolder) holder).binding.movieImg);

            }
        } else {

            // vote average is 10, and our rating bar is 5 stars: dividing by 2
            ((PopularViewHolder) holder).binding.ratingBar.setRating((mMovies.get(i).getVote_average()) / 2);

            if (mMovies.get(i).getPoster_path() != null) {
                // ImageView: Using Glide Library
                Glide.with(holder.itemView.getContext()).load(Credentials.BASE_POSTER_URL + mMovies.get(i).getPoster_path()).into(((PopularViewHolder) holder).binding.posterIv);
            }
        }
    }

    @Override
    public int getItemCount() {

        if (mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {

        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position) {

        if (mMovies != null) {
            if (mMovies.size() > 0) {
                return mMovies.get(position);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        if (Credentials.POPULAR) {
            return ListType.POPULAR.getViewType();
        } else {
            return ListType.SEARCH.getViewType();
        }
    }
}

enum ListType{
    POPULAR(1),
    SEARCH(2);


    private int viewType;

    ListType(int viewType) {

        this.viewType = viewType;
    }

    public int getViewType() {

        return viewType;
    }
}
