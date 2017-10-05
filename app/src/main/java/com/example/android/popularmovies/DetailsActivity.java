package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.popularmovies.data.Contract;
import com.example.android.popularmovies.data.DbHelper;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.Trailer;
import com.example.android.popularmovies.network.FetchReviews;
import com.example.android.popularmovies.network.FetchTrailers;
import com.example.android.popularmovies.network.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.movie_title)
    TextView title;
    @BindView(R.id.poster)
    ImageView imagePoster;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.overview)
    TextView overview;

    @BindView(R.id.text_trailer_title)
    TextView textTrailerTitle;
    @BindView(R.id.layout_trailers_list)
    LinearLayout linearLayoutTrailers;

    @BindView(R.id.text_reviews_title)
    TextView textReviewsTitle;
    @BindView(R.id.layout_reviews_list)
    LinearLayout linearLayoutReviews;

    @BindView(R.id.favorite_image)
    ImageView imageFavorite;

    private DbHelper dbHelper;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Bundle data = getIntent().getExtras();
        final Movie movie = data.getParcelable("movieDetails");
        setMovieDetails(movie);

        dbHelper = new DbHelper(this);
        if (checkIfMovieIsInDb(movie)) {
            changeToFilledFavIcon();
        }

        imageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIfMovieIsInDb(movie)) {
                    changeToFilledFavIcon();
                    saveMovieInDb(movie);
                } else {
                    changeToEmptyFavIcon();
                    deleteMovieFromDb(movie);
                }
            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            outState.putIntArray("ARTICLE_SCROLL_POSITION", new int[]{
                    scrollView.getScrollX(), scrollView.getScrollY()});
        } catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
        if (position != null) scrollView.post(new Runnable() {
            public void run() {
                scrollView.scrollTo(position[0], position[1]);
            }
        });
    }

    private void setMovieDetails(Movie movie) {
        title.setText(movie.getTitle());

        Picasso.with(imagePoster.getContext())
                .load((NetworkUtils.buildPosterUrl(movie.getPosterPath())))
                .placeholder(R.drawable.shape_movie_poster)
                .fit()
                .into(imagePoster);

        String date = movie.getReleaseDate();
        releaseDate.setText(date);

        rating.setText(movie.getDetailedVoteAverage());

        String overviewString = movie.getOverview();
        if (overviewString == null | overviewString == "") {
            overviewString = getResources().getString(R.string.no_overview);
        }
        overview.setText(overviewString);

        new FetchTrailers(String.valueOf(movie.getId())) {
            @Override
            protected void onPostExecute(List<Trailer> trailers) {
                addTrailersToLayout(trailers);
            }
        }.execute();

        new FetchReviews(String.valueOf(movie.getId())) {
            @Override
            protected void onPostExecute(List<Review> reviews) {
                addReviewsToLayout(reviews);
            }
        }.execute();
    }

    private void addTrailersToLayout(List<Trailer> trailers) {
        if (trailers != null && !trailers.isEmpty()) {
            for (Trailer trailer : trailers) {
                if (trailer.getType().equals(getString(R.string.trailer_type)) &&
                        trailer.getSite().equals(getString(R.string.trailer_site_youtube))) {
                    View view = getTrailerView(trailer);
                    linearLayoutTrailers.addView(view);
                }
            }
        } else {
            hideTrailersSection();
        }
    }

    private View getTrailerView(final Trailer trailer) {
        LayoutInflater inflater = LayoutInflater.from(DetailsActivity.this);
        View view = inflater.inflate(R.layout.trailer_list_item, linearLayoutTrailers, false);
        TextView trailerNameTextView = ButterKnife.findById(view, R.id.text_trailer_item_name);
        trailerNameTextView.setText(trailer.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(NetworkUtils.buildYouTubeUrl(trailer.getKey())));
                startActivity(intent);
            }
        });
        return view;
    }

    private void hideTrailersSection() {
        textTrailerTitle.setVisibility(View.GONE);
        linearLayoutTrailers.setVisibility(View.GONE);
    }

    private void addReviewsToLayout(List<Review> reviews) {
        if (reviews != null && !reviews.isEmpty()) {
            for (Review review : reviews) {
                View view = getReviewView(review);
                linearLayoutReviews.addView(view);
            }
        } else {
            hideReviewsSection();
        }
    }

    private View getReviewView(final Review review) {
        LayoutInflater inflater = LayoutInflater.from(DetailsActivity.this);
        View view = inflater.inflate(R.layout.review_list_item, linearLayoutReviews, false);
        TextView contentTextView = ButterKnife.findById(view, R.id.text_content_review);
        TextView authorTextView = ButterKnife.findById(view, R.id.text_author_review);
        authorTextView.setText(getString(R.string.by_author_review, review.getAuthor()));
        contentTextView.setText(review.getContent());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = review.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        return view;
    }

    private void hideReviewsSection() {
        textReviewsTitle.setVisibility(View.GONE);
        linearLayoutReviews.setVisibility(View.GONE);
    }

    private boolean checkIfMovieIsInDb(Movie movie) {
        Cursor cursor = getContentResolver().query(
                Contract.Entry.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int movieId = cursor.getInt(
                        cursor.getColumnIndex(Contract.Entry.COLUMN_MOVIE_ID));
                if (movieId == movie.getId()) {
                    return true;
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    private void changeToEmptyFavIcon() {
        imageFavorite.setImageResource(R.drawable.ic_border_star_24dp);
    }

    private void changeToFilledFavIcon() {
        imageFavorite.setImageResource(R.drawable.ic_full_star_24dp);
    }

    private void saveMovieInDb(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Entry.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(Contract.Entry.COLUMN_MOVIE_TITLE, movie.getTitle());
        contentValues.put(Contract.Entry.COLUMN_MOVIE_DESCRIPTION, movie.getOverview());
        contentValues.put(Contract.Entry.COLUMN_MOVIE_POSTER_PATH, movie.getPosterPath());
        contentValues.put(Contract.Entry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(Contract.Entry.COLUMN_MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        getContentResolver().insert(Contract.Entry.CONTENT_URI, contentValues);
    }

    private void deleteMovieFromDb(Movie movie) {
        String selection = Contract.Entry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = {String.valueOf(movie.getId())};
        getContentResolver().delete(Contract.Entry.CONTENT_URI, selection, selectionArgs);
    }
}
