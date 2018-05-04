package com.nanodegree.ibrahim.popularmovies2.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.nanodegree.ibrahim.popularmovies2.data.Contract.API_KEY;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.BAS_URL;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.POPULAR_PART;


/**
 *
 * Created by ibrahim on 28/04/18.
 */

final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();


    private static final String DEFAULT_MOVIES_URL =
            BAS_URL + POPULAR_PART + API_KEY;

    /**
     * @param selected The selected data come from menu selection that will be queried for.
     * @return The URL to use to query the movie server.
     */
    public static URL buildUrl(String selected) {
        Uri builtUri;
        /*String selected  in first time will come empty so we
        must check if it will be null i make default value of String selected
        just the url will come full not divided*/
        if (selected == null) {
            builtUri = Uri.parse(DEFAULT_MOVIES_URL).buildUpon()
                    .build();

        } else {
/* The string you will be given will be selected either as==>"popular" or ==>"top_rated" */
            builtUri = Uri.parse(BAS_URL + selected.trim() + API_KEY).buildUpon()
                    .build();
        }
        //  Return the URL used to query API
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}

