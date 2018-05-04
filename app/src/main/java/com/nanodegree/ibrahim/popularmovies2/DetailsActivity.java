package com.nanodegree.ibrahim.popularmovies2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

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
public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";

    private String mUrl;
    private String mTitle;


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

        /*make object sith value that come from intent adapter*/
        Bundle extras = getIntent().getExtras();
        //===============================================//
       /*check the data that come with inient if it is  empty  or not */
        assert extras != null;
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
}