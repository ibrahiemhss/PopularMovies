package com.nanodegree.ibrahim.popularmovies2.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.nanodegree.ibrahim.popularmovies2.interfaces.AsyncTaskCompleteListener;
import com.nanodegree.ibrahim.popularmovies2.model.Videos;

import java.net.URL;
import java.util.ArrayList;

/**
 *
 * Created by ibrahim on 05/05/18.
 */

public class FetchVideosTask extends AsyncTaskLoader< ArrayList<Videos>> {


    private final AsyncTaskCompleteListener<ArrayList<Videos>> listener;
    private final String selction;
    private final String id;
    @SuppressWarnings("unused")
    private ArrayList<Videos> videos;

    public FetchVideosTask(@NonNull Context context, AsyncTaskCompleteListener<ArrayList<Videos>> listener, String id) {
        super(context);
        this.listener = listener;
        this.selction = com.nanodegree.ibrahim.popularmovies2.data.Contract.VIDEOS;
        this.id = id;
    }

    @Override
    protected void onStartLoading() {
        if (videos != null) {
            // Delivers any previously loaded data immediately
            deliverResult(videos);
        } else {
            // Force a new load
            forceLoad();
        }
    }
    //doInBackground method to perform  network requests

    @Nullable
    @Override
    public ArrayList<Videos> loadInBackground() {
          /* If there's no zip code, there's nothing to look up. */
        // *url from methode NetworkUtils.buildUrl by parsing the selected sort of review Movie in path*/
        URL moviesRequestUrl = NetworkUtils.buildVideoUr(selction,id);

        try {
                            /*get the value json data com from url
                  return value from  OpenVedioUtils class
                   by parseing   json data  into it */
            return OpenVedioUtils
                    .getVideos(NetworkUtils
                            .getResponseFromHttpUrl(moviesRequestUrl));


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void deliverResult( ArrayList<Videos> videos) {
        super.deliverResult(videos);
        listener.onTaskComplete(videos);

    }


}

