package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.example.android.popularmovies.FavoritesAdapter;

import static com.example.android.popularmovies.MainActivity.ID_FAVORITES_LOADER;

public class CursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private FavoritesAdapter favoritesAdapter;

    public CursorLoader(Context context, FavoritesAdapter favoritesAdapter) {
        this.context = context;
        this.favoritesAdapter = favoritesAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_FAVORITES_LOADER:
                String[] projection = {
                        Contract.Entry.COLUMN_MOVIE_ID,
                        Contract.Entry.COLUMN_MOVIE_TITLE,
                        Contract.Entry.COLUMN_MOVIE_DESCRIPTION,
                        Contract.Entry.COLUMN_MOVIE_POSTER_PATH,
                        Contract.Entry.COLUMN_MOVIE_RELEASE_DATE,
                        Contract.Entry.COLUMN_MOVIE_VOTE_AVERAGE
                };
                return new android.support.v4.content.CursorLoader(context,
                        Contract.Entry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader failed " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        favoritesAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoritesAdapter.swapCursor(null);
    }
}
