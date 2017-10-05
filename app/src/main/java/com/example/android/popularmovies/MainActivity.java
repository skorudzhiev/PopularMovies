package com.example.android.popularmovies;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.example.android.popularmovies.data.CursorLoader;
import com.example.android.popularmovies.network.FetchMovies;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();
    private int selectedOption = R.id.popularity;
    private static final String FILTER_TYPE_1 = "popular";
    private static final String FILTER_TYPE_2 = "top_rated";
    private static final String FILTER_TYPE_3 = "upcoming";
    public static final int ID_FAVORITES_LOADER = 11;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private MoviesAdapter adapter;

    GridLayoutManager layoutManager;
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        layoutManager = new GridLayoutManager(this, getSpan());
        recyclerView.setLayoutManager(layoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        setMovieAdapterPopular();
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

    // Within the activity which receives these changes
    // Checks the current device orientation and prevents Restarts of the app
    // for orientation change to retain it's previous focus view.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager.setSpanCount(5);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager.setSpanCount(3);
        }
    }

    private int getSpan() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 5;
        }
        return 3;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        loadAdapterPerOptionSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void loadAdapterPerOptionSelected(int selectedOption) {
        this.selectedOption = selectedOption;
        if (selectedOption == R.id.popularity) {
            setMovieAdapterPopular();
        }
        if (selectedOption == R.id.rating) {
            setMovieAdapterTopRated();
        }
        if (selectedOption == R.id.upcoming) {
            setMovieAdapterUpcoming();
        }
        if (selectedOption == R.id.favorites) {
            setMovieAdapterFavorites();
        }
    }

    private void setMovieAdapterPopular() {
        adapter = new MoviesAdapter();
        FetchMovies moviesTask = new FetchMovies(adapter);
        recyclerView.setAdapter(adapter);
        moviesTask.execute(FILTER_TYPE_1);
    }

    private void setMovieAdapterTopRated() {
        adapter = new MoviesAdapter();
        FetchMovies moviesTask = new FetchMovies(adapter);
        recyclerView.setAdapter(adapter);
        moviesTask.execute(FILTER_TYPE_2);
    }

    private void setMovieAdapterUpcoming() {
        adapter = new MoviesAdapter();
        FetchMovies moviesTask = new FetchMovies(adapter);
        recyclerView.setAdapter(adapter);
        moviesTask.execute(FILTER_TYPE_3);
    }

    private void setMovieAdapterFavorites() {
        FavoritesAdapter favoritesAdapter = new FavoritesAdapter();
        recyclerView.setAdapter(favoritesAdapter);
        getSupportLoaderManager().initLoader(
                ID_FAVORITES_LOADER, null, new CursorLoader(this, favoritesAdapter));
    }
}