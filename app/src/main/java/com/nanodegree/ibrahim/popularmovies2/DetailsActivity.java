package com.nanodegree.ibrahim.popularmovies2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.nanodegree.ibrahim.popularmovies2.adapters.VideosAdapter;
import com.nanodegree.ibrahim.popularmovies2.data.Contract;
import com.nanodegree.ibrahim.popularmovies2.data.MovieHelper;
import com.nanodegree.ibrahim.popularmovies2.interfaces.AsyncTaskCompleteListener;
import com.nanodegree.ibrahim.popularmovies2.interfaces.OnItemClickListener;
import com.nanodegree.ibrahim.popularmovies2.model.Videos;
import com.nanodegree.ibrahim.popularmovies2.utilities.FetchVideosTask;

import java.util.ArrayList;

import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_ID;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_OVERVIEW;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_POSTER_PATH;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_RATE;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_TITLE;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_URL;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_YEAR;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.IMAGE_URL;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.W185;

/**
 *
 * @see <a href="https://stackoverflow.com/questions/26788464/how-to-change-color-of-the-back-arrow-in-the-new-material-theme">http://google.com</a>
 */
public class DetailsActivity extends AppCompatActivity implements OnItemClickListener, LoaderCallbacks<ArrayList<Videos>> {
    private static final String TAG = "DetailsActivity";
    private static final String STATE_VIDEOS = "state_videos";
    private static final int VIDEO_LOADER_ID = 0;
    private RecyclerView mRecyclerView;
    private ArrayList<Videos> videosArrayList;
    private VideosAdapter mAdapter;
    private ProgressBar mLoadingIndicator;
    private LinearLayoutManager layoutManager;
    private String extra_id, exra_title, extra_overview, extra_poster, extra_year, extra_rate;
    //  private TextView mTextfavorite;
    private MovieHelper mMovieHelper;

    @SuppressLint("PrivateResource")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_VIDEOS)) {
            //after check get the value of that key in  moviesArrayList
            videosArrayList = savedInstanceState.getParcelableArrayList(STATE_VIDEOS);
        }
        setContentView(R.layout.activity_details);
        mMovieHelper = new MovieHelper(this);
        ImageView mImag_poster = findViewById(R.id.tv_movie_poster);
        TextView mYear = findViewById(R.id.tv_year);
        TextView mOverview = findViewById(R.id.tv_overview);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView mTxtTitle = findViewById(R.id.tv_title);
        //    mTextfavorite = findViewById(R.id.tv_txt_favorite);
        //       TextView        id = findViewById(R.id.tv_id);
        setRecyclerView();
        mLoadingIndicator = findViewById(R.id.pb_loading_videos);

        /*make object sith value that come from intent adapter*/
        final Bundle extras = getIntent().getExtras();
        //===============================================//
       /*check the data that come with inient if it is  empty  or not */
        assert extras != null;
        if (extras.getString(EXTRA_ID) != null) {
            /*set Text from Intent to show the value of movie title */
            extra_id = extras.getString(EXTRA_ID);

        }
        if (extras.getString(EXTRA_TITLE) != null) {
            /*set Text from Intent to show the value of movie title */
            exra_title = extras.getString(EXTRA_TITLE);

        }
        if (extras.getString(EXTRA_POSTER_PATH) != null) {
       /*get String of movie poster Url from intent  */
            extra_poster = extras.getString(EXTRA_POSTER_PATH);

        }
        if (extras.getString(EXTRA_YEAR) != null) {
      /*set Text from Intent to show the value of  release date */
            mYear.setText(extras.getString(EXTRA_YEAR));
            extra_year = extras.getString(EXTRA_YEAR);

        }
        if (extras.getString(EXTRA_RATE) != null) {
      /*Retrieve the value of the rate from intent  and then
      calculate the value and insert it in the ratingbar*/
            int number = Integer.parseInt(extras.getString(EXTRA_RATE));
            float d = (float) ((number * 5) / 10);
            ratingBar.setRating(d);
            Log.v(TAG, "OriginalRatingValue is : " + extras.getString(EXTRA_RATE));
            extra_rate = extras.getString(EXTRA_RATE);

        }
        if (extras.getString(EXTRA_OVERVIEW) != null) {
        /*set Text from Intent to show the value of   overview */
            mOverview.setText(extras.getString(EXTRA_OVERVIEW));
            extra_overview = extras.getString(EXTRA_OVERVIEW);
        }
        if (extras.getString(EXTRA_URL) != null) {
            extra_poster = extras.getString(EXTRA_URL);

        }
        //===============================================//
        mTxtTitle.setText(exra_title);

  /*set the image by parsing the url with glide and show image from it */
        Glide.with(this)
                .load(IMAGE_URL.trim() + W185.trim() + extra_poster)
                .apply(new RequestOptions()
                                .placeholder(R.drawable.ic_image_blank)
                                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)// set exact size
                                .centerCrop()
                        // keep memory usage low by fitting into (w x h) [optional]
                )
                .into(mImag_poster);


        loadVideosData();
    }


    /**
     * Override method to put videosArrayList and
     * key  STATE_VIDEOS inside Bundle by ParcelableArrayList
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_VIDEOS, videosArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        try {
            getMenuInflater().inflate(R.menu.menu_details, menu);
            //chang icon of menu favorite if data is exist
            if (mMovieHelper.verification(extra_id)) {
                menu.findItem(R.id.menu_favorites).setIcon(R.drawable.ic_favorite_red);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onCreateOptionsMenu: error: " + e.getMessage());
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            return true;
        } else if (id == R.id.menu_favorites) {
            // if ==>id not found return false will Insert new movies data via a ContentResolver
            if (!mMovieHelper.verification(extra_id)) {
                ContentValues contentValues = new ContentValues();
                // Put the  ==>id     into ContentValues
                contentValues.put(Contract.MoviesEntry.COLUMN_MOVIES_ID, extra_id);
                // Put the  ==>title    into ContentValues
                contentValues.put(Contract.MoviesEntry.COLUMN_TITLE, exra_title);
                // Put the  ==>poster path    into ContentValues
                contentValues.put(Contract.MoviesEntry.COLUMN_POSTER_PATH, extra_poster);
                // Put the  ==>release date   into   ContentValues
                contentValues.put(Contract.MoviesEntry.COLUMN_RELEASE_DATE, extra_year);
                // Put the  ==>vot Overage     into ContentValues
                contentValues.put(Contract.MoviesEntry.COLUMN_VOTE_AVERAGE, extra_rate);
                // Put the  ==>overview     into ContentValues
                contentValues.put(Contract.MoviesEntry.COLUMN_OVERVIEW, extra_overview);
                    /* get the ContentProvider URI (movies) and Insert contentValues via a ContentResolver inside table movies*/
                Uri uri = getContentResolver().insert(Contract.MoviesEntry.CONTENT_URI, contentValues);
                if (uri != null) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.success_added), Toast.LENGTH_LONG).show();
                }
                item.setIcon(R.drawable.ic_favorite_red);

            }

            //but if ==> id found return true the second click will remove this column with this id
            else if (mMovieHelper.verification(extra_id)) {

                Uri uri = Contract.MoviesEntry.CONTENT_URI;
                if (uri != null) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.success_removed), Toast.LENGTH_LONG).show();
                }
                assert uri != null;
                uri = uri.buildUpon().appendPath(extra_id).build();

                // Delete a single row of data using a ContentResolver
                getContentResolver().delete(uri, null, null);
                item.setIcon(R.drawable.ic_favorite_gray);


            }
            return true;

        }

        return super.onContextItemSelected(item);
    }


    private void setRecyclerView() {
        OnItemClickListener listener = this;

        videosArrayList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerview_vedioes);


        layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        /*
         * The MoviesAdapter is responsible for linking our movies data with the Views that
         * will end up displaying our movies data.
         */
        mAdapter = new VideosAdapter(this, videosArrayList, listener);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mAdapter);

        //https://stackoverflow.com/questions/44843803/recyclerview-scroll-using-button-action-up-and-down-buttons-in-activity-or-fragm
        findViewById(R.id.btn_move_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalItemCount = mRecyclerView.getAdapter().getItemCount();
                if (totalItemCount <= 0) return;
                int lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition();

                if (lastVisibleItemIndex >= totalItemCount) return;
                layoutManager.smoothScrollToPosition(mRecyclerView, null, lastVisibleItemIndex + 1);
            }
        });

        findViewById(R.id.btn_move_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstVisibleItemIndex = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstVisibleItemIndex > 0) {
                    layoutManager.smoothScrollToPosition(mRecyclerView, null, firstVisibleItemIndex - 1);
                }
            }
        });
    }

    @Override
    public void onItemClick() {

    }

    private void loadVideosData() {
        showMoviesDataView();
        LoaderCallbacks<ArrayList<Videos>> callback = DetailsActivity.this;
        getSupportLoaderManager().initLoader(VIDEO_LOADER_ID, null, callback);

    }

    private void showMoviesDataView() {
        /* First, make sure the error is invisible */
        //   mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        //    mRefresh.setVisibility(View.INVISIBLE);
        /* Then, make sure the movies data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * @param loaderId The loader ID for which we need to create a loader
     * @param bundle   Any arguments supplied by the caller
     * @return A new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<ArrayList<Videos>> onCreateLoader(int loaderId, @Nullable Bundle bundle) {
        switch (loaderId) {

            case VIDEO_LOADER_ID:

                return new FetchVideosTask(this, new FetchMyDataTaskCompleteListener(),
                        extra_id);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Videos>> loader, ArrayList<Videos> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Videos>> loader) {

    }

    private class FetchMyDataTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Videos>> {

        @Override
        public void onTaskComplete(ArrayList<Videos> result) {
            // do something with the result
            //after loading data Progress Bar will disappear
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (result != null) {
                showMoviesDataView();
                /*set the the of our moviesArrayList from the value that com from asyncTask ( onPostExecute  parameter
                 * to save it inside onSaveInstanceState
                 * */
                videosArrayList = result;
                /*ubdate the value of mAdapter by sending the value of arraylist inside it* */
                mAdapter.updateVideos(videosArrayList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }


}