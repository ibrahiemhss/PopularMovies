package com.nanodegree.ibrahim.popularmovies2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nanodegree.ibrahim.popularmovies2.adapters.MoviesAdapter;
import com.nanodegree.ibrahim.popularmovies2.data.SharedPrefManager;
import com.nanodegree.ibrahim.popularmovies2.interfaces.AsyncTaskCompleteListener;
import com.nanodegree.ibrahim.popularmovies2.interfaces.OnItemClickListener;
import com.nanodegree.ibrahim.popularmovies2.model.Movies;
import com.nanodegree.ibrahim.popularmovies2.utilities.FetchMovieFromSqlite;
import com.nanodegree.ibrahim.popularmovies2.utilities.FetchMoviesTaskLoader;
import android.support.v4.app.LoaderManager.LoaderCallbacks;

import java.util.ArrayList;

import static com.nanodegree.ibrahim.popularmovies2.data.Contract.POPULAR_PART;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.TOP_RATED_PART;


public class MainActivity extends AppCompatActivity implements OnItemClickListener , LoaderManager.LoaderCallbacks<ArrayList<Movies>> {
    private static final String TAG = "Movies";
    private static final String STATE_MOVIES = "state_movies";
    private RecyclerView mRecyclerView;
   private TextView mErrorMessageDisplay;
    private Button mRefresh;
    private ProgressBar mLoadingIndicator;
    private ArrayList<Movies> moviesArrayList;
    private MoviesAdapter mAdapter;
    private Menu menu;
    private static final int MOVIE_LOADER_ID = 2;
    private LoaderCallbacks<ArrayList<Movies>> callback;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*make sure if  savedInstanceState not null after rotate or exit the application
         * snd it have the key that come from onSaveInstanceState with Bundle
         * */
        callback = MainActivity.this;
        OnItemClickListener listener = this;
        moviesArrayList = new ArrayList<>();
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_MOVIES)) {
            //after check get the value of that key in  moviesArrayList
            moviesArrayList = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
        }
        setContentView(R.layout.activity_main);

        /*set default value to favorite to false to not get the favorite when openinig activity*/
        isFavorite=false;


        mRecyclerView = findViewById(R.id.recyclerview_movies);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        /*
         * GridLayoutManager get with two parameters context
         * & integer to make two horizontal raw every item in recyclerView
         */
        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        /*
         * The MoviesAdapter is responsible for linking our movies data with the Views that
         * will end up displaying our movies data.
         */
        mAdapter = new MoviesAdapter(this, moviesArrayList, listener);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading
         */
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mRefresh = findViewById(R.id.btn_refresh);

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.updateMovies(null);
                ConnectivityManager cm =
                        (ConnectivityManager)MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

                assert cm != null;
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                assert activeNetwork != null;
                boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
                if(!isConnected || !isWiFi){
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.check_intenet),Toast.LENGTH_LONG).show();
                }else {
                    loadMoviesData();

                }

            }
        });
        /* Once all of our views are setup, we can load the movies data. */
     loadMoviesData();


    }

    /**
     * background method to get the movies data in the background.
     */
    private void loadMoviesData() {
        /*
         * This ID will uniquely identify the Loader. We can use it,to get a handle
         * on our Loader at a later point in time through the support LoaderManager.
         */
        /*
         * From MainActivity, we have implemented the LoaderCallbacks interface with the type of
         * String array. (implements  LoaderCallbacks<ArrayList<Movies>>) The variable callback is passed
         * to the call to initLoader below. This means that whenever the loaderManager has
         * something to notify us of, it will do so through this callback.
         */
          /*
         * The second parameter of the initLoader method below is a Bundle. Optionally, you can
         * pass a Bundle to initLoader that you can then access from within the onCreateLoader
         * callback. In our case, we don't actually use the Bundle, but it's here in case we wanted
         * to.
         */
           /*
         * Ensures a loader is initialized and active. If the loader doesn't already exist, one is
         * created and (if the activity/fragment is currently started) starts the loader. Otherwise
         * the last created loader is re-used.
         */
        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, callback);

    }

    /**
     * Override method to put moviesArrayList and
     * key  STATE_MOVIES inside Bundle by ParcelableArrayList
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_MOVIES, moviesArrayList);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(STATE_MOVIES, moviesArrayList);
        super.onRestoreInstanceState(savedInstanceState);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_last:
                isFavorite=false;
                //change title of menu  that show in  toolbar by sellected itme text
                setMenuName(TOP_RATED_PART);
                invalidateData();
                //save String==> top_rated   so will change the URL and therefore bring the json based on==> top_rated
                SharedPrefManager.getInstance(MainActivity.this).setPrefUrlSellected(TOP_RATED_PART);
                //recall method  loadMoviesData to get data from new url
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);

                return true;
            case R.id.menu_popularity:
                invalidateData();
                //change title of menu  that show in  toolbar by sellected itme text
                setMenuName(POPULAR_PART);
                isFavorite=false;

                //save String==> popular   so will change the URL and therefore bring the json based on ==>popular
                SharedPrefManager.getInstance(MainActivity.this).setPrefUrlSellected(POPULAR_PART);
                //recall method  loadMoviesData to get data from new url
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
                return true;
            case R.id.menu_favorites:
                invalidateData();
                //change title of menu  that show in  toolbar by sellected itme text
                setMenuName(getResources().getString(R.string.favorites));
                /*set value of isFavorite to make loader  read date from FetchMovieFromSqlite class*/
                isFavorite=true;
                //restart loader to get new value of favorites
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        try {
            getMenuInflater().inflate(R.menu.main_menu, menu);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onCreateOptionsMenu: error: " + e.getMessage());
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void setMenuName(String set) {
        menu.findItem(R.id.selected).setTitle(set);
    }

    /**
     * This method will make the View for the movies data visible and
     * hide the error message.
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check Movies each view is currently visible or invisible.
     */
    private void showMoviesDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRefresh.setVisibility(View.INVISIBLE);
        /* Then, make sure the movies data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the movies
     * View.
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check movies each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setText(getResources().getString(R.string.error_message));
        mRefresh.setVisibility(View.VISIBLE);

    }


      /** @param loaderId The loader ID for which we need to create a loader
         * @param bundle   Any arguments supplied by the caller
        * @return A new Loader instance that is ready to start loading.
            */
    @   NonNull
    @Override
    public Loader<ArrayList<Movies>> onCreateLoader(int loaderId, @Nullable Bundle bundle) {
        switch (loaderId) {

            case MOVIE_LOADER_ID:
           /*if statement here to change source of data from internet or from sqlite as favorites*/
                if (isFavorite) {
          /*return data from FetchMovieFromSqlite class*/
                    return new FetchMovieFromSqlite(this, new AsyncTaskCompleteListener<ArrayList<Movies>>() {
                        @Override
                        public void onTaskComplete(ArrayList<Movies> result) {
                            // method that show  text message "no  favorites added "
                            isImpty(result);

                        }
                    });

                } else {
         /*any thing not true  data will returned  from FetchMoviesTaskLoader class*/

                    return new FetchMoviesTaskLoader(this, new AsyncTaskCompleteListener<ArrayList<Movies>>() {
                        @Override
                        public void onTaskComplete(ArrayList<Movies> result) {

                        }
                    });
                }

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }

    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movies>> loader, ArrayList<Movies> result) {
        // do something with the result
        //after loading data Progress Bar will disappear
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        if (result != null) {
            showMoviesDataView();
                /*set the the of our moviesArrayList from the value that com from asyncTask ( onPostExecute  parameter
                 * to save it inside onSaveInstanceState
                 * */
            moviesArrayList=result;
                /*ubdate the value of mAdapter by sending the value of arraylist inside it* */
            mAdapter.updateMovies(moviesArrayList);


            mAdapter.notifyDataSetChanged();
        }
        else {
            showErrorMessage();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movies>> data) {


    }
    /**
     * This method is used when we are resetting data, so that at one point in time during a
     * refresh of our data, you can see that there is no data showing.
     */
    private void invalidateData() {
        mAdapter.updateMovies(null);
    }

    /**
     * This method is used when we get empty data from sqlite has no favorite added
     *   @param movies   will come from loader
     */
    private void isImpty(ArrayList<Movies> movies){
        if(movies.size()==0){
            mErrorMessageDisplay.setVisibility(View.VISIBLE);
            mErrorMessageDisplay.setText(getResources().getString(R.string.no_favorites));
        }
    }

    @Override
    public void onItemClick() {

    }
}