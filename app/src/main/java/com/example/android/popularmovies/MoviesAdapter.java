package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.network.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieAdapterViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    public MoviesAdapter() {
    }

    public void setMoviesData(List<Movie> list) {
        movies = list;
        notifyDataSetChanged();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.image_poster) ImageView imagePoster;

        public MovieAdapterViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent (view.getContext(), DetailsActivity.class);
            Movie currentMovie = movies.get(getAdapterPosition());
            intent.putExtra("movieDetails", currentMovie);
            view.getContext().startActivity(intent);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        MovieAdapterViewHolder viewHolder = new MovieAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Picasso.with(holder.imagePoster.getContext())
                .load(NetworkUtils.buildPosterUrl(movies.get(position).getPosterPath()))
                .fit()
                .placeholder(R.drawable.shape_movie_poster)
                .into(holder.imagePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}

