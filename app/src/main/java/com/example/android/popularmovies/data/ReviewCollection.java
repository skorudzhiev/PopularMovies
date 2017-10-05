package com.example.android.popularmovies.data;

import java.util.List;

public class ReviewCollection {
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "ReviewCollection{" +
                "reviews=" + reviews +
                '}';
    }
}
