package com.example.android.popularmovies.network;

import android.os.AsyncTask;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.data.Trailer;
import com.example.android.popularmovies.data.TrailerCollection;

import java.net.URL;
import java.util.List;
import static com.example.android.popularmovies.network.NetworkUtils.buildTrailersOrReviewsUrl;
import static com.example.android.popularmovies.network.NetworkUtils.getResponseFromHttpUrl;
import static com.example.android.popularmovies.network.TrailersJsonUtils.parseJson;

public abstract class FetchTrailers extends AsyncTask<String, Void, List<Trailer>> {
    private String id;

    public FetchTrailers(String id) {
        this.id = id;
    }

    @Override
    protected List<Trailer> doInBackground(String... params) {
        URL trailersRequestUrl = buildTrailersOrReviewsUrl(BuildConfig.API_KEY, id, "videos");
        try {
            String jsonTrailersResponse = getResponseFromHttpUrl(trailersRequestUrl);
            TrailerCollection trailerCollection = parseJson(jsonTrailersResponse);
            return trailerCollection.getTrailers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
