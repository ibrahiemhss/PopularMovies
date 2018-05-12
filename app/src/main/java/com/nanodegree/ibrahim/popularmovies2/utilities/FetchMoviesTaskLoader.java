package com.nanodegree.ibrahim.popularmovies2.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.nanodegree.ibrahim.popularmovies2.data.SharedPrefManager;
import com.nanodegree.ibrahim.popularmovies2.interfaces.AsyncTaskCompleteListener;
import com.nanodegree.ibrahim.popularmovies2.model.Movies;

import java.net.URL;
import java.util.ArrayList;

/**
 *
 * Created by ibrahim on 08/05/18.
 */
public class FetchMoviesTaskLoader extends AsyncTaskLoader<ArrayList<Movies>> {
    private static final String TAG = "FetchMoviesTaskLoader";
    private final AsyncTaskCompleteListener<ArrayList<Movies>> listener;
    private ArrayList<Movies> movies;

    public FetchMoviesTaskLoader(@NonNull Context context, AsyncTaskCompleteListener<ArrayList<Movies>> listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onStartLoading() {
        if (movies != null) {
            // Delivers any previously loaded data immediately
            deliverResult(movies);
        } else {
            // Force a new load
            forceLoad();
        }
    }

    @Nullable
    @Override
    public ArrayList<Movies> loadInBackground() {
        SharedPrefManager.getInstance(getContext()).getPrefUrlSellected();
        URL moviesRequestUrl = NetworkUtils.buildUrl(SharedPrefManager.getInstance(getContext()).getPrefUrlSellected());

        String surl = String.valueOf(moviesRequestUrl);

        try {
        /*get the value json data com from url
          return value from  OpenMoviesUtils class
           by parseing   json data  into it */
            Log.v(TAG, "myUrlIs:" + surl);

            return OpenMoviesUtils.getMovies(NetworkUtils
                    .getResponseFromHttpUrl(moviesRequestUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void deliverResult(ArrayList<Movies> movi) {
        movies = movi;
        super.deliverResult(movi);
        listener.onTaskComplete(movi);

    }

}
