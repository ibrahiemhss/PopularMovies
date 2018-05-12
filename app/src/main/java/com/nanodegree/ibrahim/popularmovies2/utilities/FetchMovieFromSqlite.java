package com.nanodegree.ibrahim.popularmovies2.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.nanodegree.ibrahim.popularmovies2.interfaces.AsyncTaskCompleteListener;
import com.nanodegree.ibrahim.popularmovies2.model.Movies;

import java.util.ArrayList;

/**
 *
 * Created by ibrahim on 11/05/18.
 */

public class FetchMovieFromSqlite extends AsyncTaskLoader<ArrayList<Movies>> {
    private final AsyncTaskCompleteListener<ArrayList<Movies>> listener;
    private ArrayList<Movies> movies;

    public FetchMovieFromSqlite(@NonNull Context context, AsyncTaskCompleteListener<ArrayList<Movies>> listener) {
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

        try {
     /*get the value arraylist data com from sqlite
        return value from  OpenFavoriteUtils class */
            return OpenFavoriteUtils.getMovies(getContext());
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

