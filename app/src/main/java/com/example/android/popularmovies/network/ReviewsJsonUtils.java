package com.example.android.popularmovies.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.ReviewCollection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies.MainActivity.LOG_TAG;

public class ReviewsJsonUtils {

    private static final String KEY_ERROR = "status_code";
    private static final String KEY_REVIEWS = "results";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_URL = "url";

    public static ReviewCollection parseJson(String json)
            throws JSONException {
        JSONObject responseJson = new JSONObject(json);
        if (responseJson.has(KEY_ERROR)) {
            int errorCode = responseJson.getInt(KEY_ERROR);
            Log.e(LOG_TAG, "parse json reviews error code: " + String.valueOf(errorCode));
        }
        JSONArray reviewsArray = responseJson.getJSONArray(KEY_REVIEWS);
        List<Review> reviewList = parseReviewList(reviewsArray);
        ReviewCollection reviewCollection = new ReviewCollection();
        reviewCollection.setReviews(reviewList);
        return reviewCollection;
    }

    @NonNull
    private static List<Review> parseReviewList(JSONArray reviewArray) throws JSONException {
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < reviewArray.length(); i++) {
            JSONObject review = reviewArray.getJSONObject(i);
            Review currentReview = parseReview(review);
            reviews.add(currentReview);
        }
        return reviews;
    }

    @NonNull
    private static Review parseReview(JSONObject review) throws JSONException {
        Review currentReview = new Review();
        currentReview.setAuthor(review.getString(KEY_AUTHOR));
        currentReview.setContent(review.getString(KEY_CONTENT));
        currentReview.setUrl(review.getString(KEY_URL));
        return currentReview;
    }
}
