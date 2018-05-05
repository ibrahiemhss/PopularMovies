package com.nanodegree.ibrahim.popularmovies2.utilities;

import android.os.AsyncTask;

import com.nanodegree.ibrahim.popularmovies2.interfaces.AsyncTaskCompleteListener;
import com.nanodegree.ibrahim.popularmovies2.model.Videos;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ibrahim on 05/05/18.
 */

public class FetchVideosTask extends AsyncTask<String, Void, ArrayList<Videos>> {

    private final AsyncTaskCompleteListener<ArrayList<Videos>> listener;
    private final String selction;
    private final String id;

    public FetchVideosTask(AsyncTaskCompleteListener<ArrayList<Videos>> listener, String selction, String id) {
        this.listener = listener;
        this.selction = selction;
        this.id = id;
    }


    //doInBackground method to perform  network requests
    @Override
    protected ArrayList<Videos> doInBackground(String... params) {



                            /* If there's no zip code, there's nothing to look up. */
        // *url from methode NetworkUtils.buildUrl by parsing the selected sort of review Movie in path*/
        URL moviesRequestUrl = NetworkUtils.buildVideoUr(selction,id);

        try {
                            /*get the value json data com from url
                  return value from  OpenMoviesUtils class
                   by parseing   json data  into it */
            return OpenVedioUtils
                    .getVideos(NetworkUtils
                            .getResponseFromHttpUrl(moviesRequestUrl));


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Videos>videosData) {
        super.onPostExecute(videosData);
        listener.onTaskComplete(videosData);
    }


}

