package com.nanodegree.ibrahim.popularmovies2.utilities;

import android.os.AsyncTask;


import com.nanodegree.ibrahim.popularmovies2.interfaces.AsyncTaskCompleteListener;
import com.nanodegree.ibrahim.popularmovies2.model.Movies;

import java.net.URL;
import java.util.ArrayList;

//perform network requests by AsyncTask
public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movies>> {

    private final AsyncTaskCompleteListener<ArrayList<Movies>> listener;
    private final String selction;

    public FetchMoviesTask(AsyncTaskCompleteListener<ArrayList<Movies>> listener, String selction) {
        this.listener = listener;
        this.selction = selction;
    }


    //doInBackground method to perform  network requests
    @Override
    protected ArrayList<Movies> doInBackground(String... params) {



                            /* If there's no zip code, there's nothing to look up. */
        // *url from methode NetworkUtils.buildUrl by parsing the selected sort of review Movie in path*/
        URL moviesRequestUrl = NetworkUtils.buildUrl(selction);

        try {
                            /*get the value json data com from url
                  return value from  OpenMoviesUtils class
                   by parseing   json data  into it */
            return OpenMoviesUtils
                    .getMovies(NetworkUtils
                            .getResponseFromHttpUrl(moviesRequestUrl));


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Movies> MoviesData) {
        super.onPostExecute(MoviesData);
        listener.onTaskComplete(MoviesData);
    }


}

