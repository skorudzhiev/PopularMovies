package com.example.android.popularmovies.data;

import java.util.List;

public class MovieCollection {

    List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "MovieCollection{" +
                "movies=" + movies +
                '}';
    }
}
