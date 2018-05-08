package com.nanodegree.ibrahim.popularmovies2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.nanodegree.ibrahim.popularmovies2.adapters.VideosAdapter;
import com.nanodegree.ibrahim.popularmovies2.interfaces.AsyncTaskCompleteListener;
import com.nanodegree.ibrahim.popularmovies2.interfaces.OnItemClickListener;
import com.nanodegree.ibrahim.popularmovies2.model.Movies;
import com.nanodegree.ibrahim.popularmovies2.model.Videos;
import com.nanodegree.ibrahim.popularmovies2.utilities.FetchVideosTask;
import android.support.v4.app.LoaderManager.LoaderCallbacks;


import java.util.ArrayList;

import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_ID;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_OVERVIEW;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_RATE;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_TITLE;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_URL;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_YEAR;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.IMAGE_URL;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.W185;

/**
 * @see <a href="https://stackoverflow.com/questions/26788464/how-to-change-color-of-the-back-arrow-in-the-new-material-theme">http://google.com</a>
 */
public class DetailsActivity extends AppCompatActivity implements OnItemClickListener  , LoaderCallbacks<ArrayList<Videos>> {
    private static final String TAG = "DetailsActivity";

    private String mUrl;
    private String mTitle;
    private RecyclerView mRecyclerView;
    private ArrayList<Videos> videosArrayList;
    private VideosAdapter mAdapter;
    private ProgressBar mLoadingIndicator;
    private LinearLayoutManager layoutManager;
private String extra_id;
    private static final int MOVIE_LOADER_ID = 0;


    @SuppressLint("PrivateResource")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView mImag_poster = findViewById(R.id.tv_movie_poster);
        TextView mYear = findViewById(R.id.tv_year);
        TextView mOverview = findViewById(R.id.tv_overview);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView mTxtTitle = findViewById(R.id.tv_title);
  //       TextView        id = findViewById(R.id.tv_id);
        setRecyclerView();
        mLoadingIndicator = findViewById(R.id.pb_loading_videos);

        /*make object sith value that come from intent adapter*/
        Bundle extras = getIntent().getExtras();
        //===============================================//
       /*check the data that come with inient if it is  empty  or not */
        assert extras != null;
        if (extras.getString(EXTRA_ID) != null) {
            /*set Text from Intent to show the value of movie title */
            extra_id=extras.getString(EXTRA_ID);

        }
        if (extras.getString(EXTRA_TITLE) != null) {
            /*set Text from Intent to show the value of movie title */
            mUrl = extras.getString(EXTRA_TITLE);

        }
        if (extras.getString(EXTRA_URL) != null) {
       /*get String of movie poster Url from intent  */
            mTitle = extras.getString(EXTRA_URL);

        }
        if (extras.getString(EXTRA_YEAR) != null) {
      /*set Text from Intent to show the value of  release date */
            mYear.setText(extras.getString(EXTRA_YEAR));

        }
        if (extras.getString(EXTRA_RATE) != null) {
      /*Retrieve the value of the rate from intent  and then
      calculate the value and insert it in the ratingbar*/
            int number = Integer.parseInt(extras.getString(EXTRA_RATE));
            float d = (float) ((number * 5) / 10);
            ratingBar.setRating(d);
            Log.v(TAG, "OriginalRatingValue is : " + extras.getString(EXTRA_RATE));

        }
        if (extras.getString(EXTRA_OVERVIEW) != null) {
        /*set Text from Intent to show the value of   overview */
            mOverview.setText(extras.getString(EXTRA_OVERVIEW));

        }
        if (extras.getString(EXTRA_URL) != null) {
            mTitle = extras.getString(EXTRA_URL);

        }
        //===============================================//
        mTxtTitle.setText(mTitle);

        /*
         * Created by ibrahim on 30/12/17.
         * SharedPrefManager will save the value of selected sort of show that will base in query url
         *
         */

  /*set the image by parsing the url with glide and show image from it */
        Glide.with(this)
                .load(IMAGE_URL.trim() + W185.trim() + mUrl)
                .apply(new RequestOptions()
                                .placeholder(R.drawable.ic_image_blank)
                                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)// set exact size
                                .centerCrop()
                        // keep memory usage low by fitting into (w x h) [optional]
                )
                .into(mImag_poster);


        loadVideosData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView(){
                OnItemClickListener listener = this;

        videosArrayList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerview_vedioes);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

     //   mRecyclerView.setLayoutManager(layoutManager);

      //  mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
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
                layoutManager.smoothScrollToPosition(mRecyclerView,null,lastVisibleItemIndex+1);
            }
        });

      findViewById(R.id.btn_move_forward).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              int firstVisibleItemIndex = layoutManager.findFirstCompletelyVisibleItemPosition();
              if (firstVisibleItemIndex > 0) {
                  layoutManager.smoothScrollToPosition(mRecyclerView,null,firstVisibleItemIndex-1);
              }
          }
      });
    }

    @Override
    public void onItemClick() {

    }
    private void loadVideosData() {
        showMoviesDataView();
        int loaderId = MOVIE_LOADER_ID;

        LoaderCallbacks<ArrayList<Videos>> callback = DetailsActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);

    }

    private void showMoviesDataView() {
        /* First, make sure the error is invisible */
     //   mErrorMessageDisplay.setVisibility(View.INVISIBLE);
    //    mRefresh.setVisibility(View.INVISIBLE);
        /* Then, make sure the movies data is visible */
       mRecyclerView.setVisibility(View.VISIBLE);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Videos>> onCreateLoader(int id, @Nullable Bundle args) {
        return   new FetchVideosTask(this,new FetchMyDataTaskCompleteListener(),
                extra_id);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Videos>> loader, ArrayList<Videos> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Videos>> loader) {

    }

    private class FetchMyDataTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Videos>>
    {

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
        }}


}