package com.example.android.popularmovies.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    private Contract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class Entry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_DESCRIPTION = "movieDescription";
        public static final String COLUMN_MOVIE_POSTER_PATH = "moviePosterPath";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movieReleaseDate";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movieVoteAverage";

        public static final String CREATE_TABLE_MOVIES = "CREATE TABLE " +
                Entry.TABLE_NAME + "(" +
                Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Entry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                Entry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
                Entry.COLUMN_MOVIE_DESCRIPTION + " TEXT NOT NULL," +
                Entry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL," +
                Entry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
                Entry.COLUMN_MOVIE_VOTE_AVERAGE + " LONG NOT NULL" +
                ");";
    }
}
