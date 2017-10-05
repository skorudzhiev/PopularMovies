package com.example.android.popularmovies.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieCollection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies.MainActivity.LOG_TAG;

public class MoviesJsonUtils {
    private static final String KEY_ERROR = "status_code";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ID = "id";
    private static final String KEY_VOTE_AVERAGE = "vote_average";

    public static MovieCollection parseJson(String json)
            throws JSONException {
        JSONObject responseJson = new JSONObject(json);
        if (responseJson.has(KEY_ERROR)) {
            int errorCode = responseJson.getInt(KEY_ERROR);
            Log.e(LOG_TAG, "parse json movies error code: " + String.valueOf(errorCode));
        }
        JSONArray moviesArray = responseJson.getJSONArray(KEY_RESULTS);
        List<Movie> moviesList = parseMovieList(moviesArray);
        MovieCollection movieCollection = new MovieCollection();
        movieCollection.setMovies(moviesList);
        return movieCollection;
    }

    @NonNull
    private static List<Movie> parseMovieList(JSONArray moviesArray) throws JSONException {
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movie = moviesArray.getJSONObject(i);
            Movie currentMovie = parseMovie(movie);
            movieList.add(currentMovie);
        }
        return movieList;
    }

    @NonNull
    private static Movie parseMovie(JSONObject movie) throws JSONException {
        Movie currentMovie = new Movie();
        currentMovie.setPosterPath(movie.getString(KEY_POSTER_PATH));
        currentMovie.setOverview(movie.getString(KEY_OVERVIEW));
        currentMovie.setReleaseDate(movie.getString(KEY_RELEASE_DATE));
        currentMovie.setTitle(movie.getString(KEY_TITLE));
        currentMovie.setId(movie.getInt(KEY_ID));
        currentMovie.setVoteAverage(movie.getDouble(KEY_VOTE_AVERAGE));
        return currentMovie;
    }
}
