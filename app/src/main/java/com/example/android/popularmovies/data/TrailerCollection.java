package com.example.android.popularmovies.data;

import java.util.List;

public class TrailerCollection {

    private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public String toString() {
        return "TrailerCollection{" +
                "trailers=" + trailers +
                '}';
    }
}
