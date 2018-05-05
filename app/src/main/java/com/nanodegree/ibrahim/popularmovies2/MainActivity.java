package com.nanodegree.ibrahim.popularmovies2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import com.nanodegree.ibrahim.popularmovies2.utilities.FetchMoviesTask;

import java.util.ArrayList;

import static com.nanodegree.ibrahim.popularmovies2.data.Contract.POPULAR_PART;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.TOP_RATED_PART;


public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "Movies";
    private static final String STATE_MOVIES = "state_movies";
    private RecyclerView mRecyclerView;
   private TextView mErrorMessageDisplay;
    private Button mRefresh;
    private ProgressBar mLoadingIndicator;
    private ArrayList<Movies> moviesArrayList;
    private MoviesAdapter mAdapter;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*make sure if  savedInstanceState not null after rotate or exit the application
         * snd it have the key that come from onSaveInstanceState with Bundle
         * */
        OnItemClickListener listener = this;
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_MOVIES)) {
            //after check get the value of that key in  moviesArrayList
            moviesArrayList = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
        }
        setContentView(R.layout.activity_main);

        moviesArrayList = new ArrayList<>();
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
        showMoviesDataView();
        //pass selcted String param from SharedPrefManager
        if( SharedPrefManager.getInstance(this).getPrefUrlSellected()==null){
            new FetchMoviesTask(new FetchMyDataTaskCompleteListener(),
                    POPULAR_PART).execute();
        }
        new FetchMoviesTask(new FetchMyDataTaskCompleteListener(),
                SharedPrefManager.getInstance(this).getPrefUrlSellected()).execute();
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_last:
                //change title of menu  that show in  toolbar by sellected itme text
                setMenuName(TOP_RATED_PART);
                //save String==> top_rated   so will change the URL and therefore bring the json based on==> top_rated
                SharedPrefManager.getInstance(MainActivity.this).setPrefUrlSellected(TOP_RATED_PART);
                //recall method  loadMoviesData to get data from new url
                loadMoviesData();

                return true;
            case R.id.menu_popularity:
                //change title of menu  that show in  toolbar by sellected itme text
                setMenuName(POPULAR_PART);
                //save String==> popular   so will change the URL and therefore bring the json based on ==>popular
                SharedPrefManager.getInstance(MainActivity.this).setPrefUrlSellected(POPULAR_PART);
                //recall method  loadMoviesData to get data from new url
                loadMoviesData();

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
        mRefresh.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemClick() {

    }

    private class FetchMyDataTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Movies>>
    {

        @Override
        public void onTaskComplete(ArrayList<Movies> result)
        {
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
    }



}