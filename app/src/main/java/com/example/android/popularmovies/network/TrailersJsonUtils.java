package com.example.android.popularmovies.network;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.android.popularmovies.data.Trailer;
import com.example.android.popularmovies.data.TrailerCollection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies.MainActivity.LOG_TAG;

public class TrailersJsonUtils {

    private static final String KEY_ERROR = "status_code";
    private static final String KEY_TRAILERS = "results";
    private static final String KEY_KEY = "key";
    private static final String KEY_NAME = "name";
    private static final String KEY_SITE = "site";
    private static final String KEY_TYPE = "type";

    public static TrailerCollection parseJson(String json)
            throws JSONException {
        JSONObject responseJson = new JSONObject(json);
        if (responseJson.has(KEY_ERROR)) {
            int errorCode = responseJson.getInt(KEY_ERROR);
            Log.e(LOG_TAG, "parse json trailers error code: " + String.valueOf(errorCode));
        }
        JSONArray trailersArray = responseJson.getJSONArray(KEY_TRAILERS);
        List<Trailer> trailerList = parseTrailerList(trailersArray);
        TrailerCollection trailerCollection = new TrailerCollection();
        trailerCollection.setTrailers(trailerList);
        return trailerCollection;
    }

    @NonNull
    private static List<Trailer> parseTrailerList(JSONArray trailerArray) throws JSONException {
        List<Trailer> trailers = new ArrayList<>();
        for (int i = 0; i < trailerArray.length(); i++) {
            JSONObject trailer = trailerArray.getJSONObject(i);
            Trailer currentTrailer = parseTrailer(trailer);
            trailers.add(currentTrailer);
        }
        return trailers;
    }

    @NonNull
    private static Trailer parseTrailer(JSONObject trailer) throws JSONException {
        Trailer currentTrailer = new Trailer();
        currentTrailer.setKey(trailer.getString(KEY_KEY));
        currentTrailer.setName(trailer.getString(KEY_NAME));
        currentTrailer.setSite(trailer.getString(KEY_SITE));
        currentTrailer.setType(trailer.getString(KEY_TYPE));
        return currentTrailer;
    }
}
