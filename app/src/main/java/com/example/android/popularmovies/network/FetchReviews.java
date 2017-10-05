package com.example.android.popularmovies.network;

import android.os.AsyncTask;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.ReviewCollection;

import java.net.URL;
import java.util.List;
public abstract class FetchReviews extends AsyncTask<String, Void, List<Review>> {

    private static final String LOG_TAG = FetchReviews.class.getSimpleName();
    private String id;

    public FetchReviews(String id) {
        this.id = id;
    }

    @Override
    protected List<Review> doInBackground(String... params) {
        URL reviewsRequestUrl = NetworkUtils.buildTrailersOrReviewsUrl(BuildConfig.API_KEY, id, "reviews");
        try {
            String jsonReviewsResponse = NetworkUtils
                    .getResponseFromHttpUrl(reviewsRequestUrl);
            ReviewCollection reviewCollection = ReviewsJsonUtils.parseJson(jsonReviewsResponse);
            return reviewCollection.getReviews();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
