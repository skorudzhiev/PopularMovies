package com.example.android.popularmovies.network;
import android.os.AsyncTask;
import static com.example.android.popularmovies.network.NetworkUtils.buildUrl;
import static com.example.android.popularmovies.network.NetworkUtils.getResponseFromHttpUrl;


import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.MoviesAdapter;
import com.example.android.popularmovies.data.MovieCollection;

import java.net.URL;
import java.util.List;

public class FetchMovies extends AsyncTask<String, Void, List<Movie>> {

    private MoviesAdapter adapter;

    public FetchMovies(MoviesAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        URL moviesRequestUrl = buildUrl(BuildConfig.API_KEY, params[0]);
        try {
            String jsonMoviesResponse = getResponseFromHttpUrl(moviesRequestUrl);
            MovieCollection movieCollection = MoviesJsonUtils.parseJson(jsonMoviesResponse);
            return movieCollection.getMovies();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            adapter.setMoviesData(movies);
        }
    }
}
