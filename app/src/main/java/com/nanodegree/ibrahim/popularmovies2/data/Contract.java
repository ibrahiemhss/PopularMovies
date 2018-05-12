package com.nanodegree.ibrahim.popularmovies2.data;


import android.net.Uri;
import android.provider.BaseColumns;

import com.nanodegree.ibrahim.popularmovies2.BuildConfig;

/**
 *
 * Created by ibrahim on 01/05/18.
 *
 */

public class Contract {
    private static final String KEY = "?api_key=";


    //TODO put your own Api key
    public static final String API_KEY = KEY+ BuildConfig.API_KEY;
    public static final String BAS_URL = "https://api.themoviedb.org/3/movie/";
    public static final String POPULAR_PART = "popular";
    public static final String TOP_RATED_PART = "top_rated ";
    public static final String VIDEOS = "videos ";

    //keyes of intent
    public static final String EXTRA_TITLE = "extra_title ";
    public static final String EXTRA_URL = "extra_url ";
    public static final String EXTRA_YEAR = "extra_year ";
    public static final String EXTRA_RATE = "extra_rate ";
    public static final String EXTRA_OVERVIEW = "extra_overview ";
    public static final String EXTRA_ID = "extra_id ";
    public static final String EXTRA_WEBVIEW_URL = "web_view_url ";
    public static final String EXTRA_FAVORITE = "extra_favorite";
    public static final String EXTRA_POSTER_PATH = "extra_poster_path";

    //the url of value of image view
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static final String W185 = "w185";
    public static final String W500 = "w500";



    /* Content provider constants to the Contract
     Clients need to know how to access the movies data,  to provide
     these content URI's for the path to that data:
        1) Content authority,
        2) Base content URI,
        3) Path(s) to the movies directory
        4) Content URI for data in the MoviesEntry class
      */

    // The authority, which is how our code knows which Content Provider to access
    public static final String AUTHORITY = "com.nanodegree.ibrahim.popularmovies2";

    // The base content URI = "content://" + <authority>
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_MOVIES = "movies";

    /* Inner class that defines the table contents of the Movies table */
    public static final class MoviesEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        /* Used internally as the name of our Movies table. */
            public static final String TABLE_NAME = "movies";
            public static final String COLUMN_MOVIES_ID = "movies_id";
            public static final String COLUMN_TITLE = "title";
            public static final String COLUMN_VOTE_AVERAGE = "vote_average";
            public static final String COLUMN_POSTER_PATH = "poster_path";
            public static final String COLUMN_OVERVIEW = "overview";
            public static final String COLUMN_RELEASE_DATE = "release_date";
/*
        The above table structure looks something like the sample table below.
        With the name of the table and columns on top, and potential contents in rows

        Note: Because this implements BaseColumns, the _id column is coming from api

        movies
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - -
        | movies_id  |                    title                        |     favorite      |    vote_average   |    poster_path   |              overview         |   release_date |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - -
        |  299536     |   Avengers: Infinity War   |            0            |           8.6                 |      Fpyyt.jpg         | overview of Movie  |   2018-04-25    |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - -
        |  337167     |  Fifty Shades Freed          |              1            |           6.6                 |        Fpyyt.jpg      | overview of Movie  |   2016-02-11      |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - -
        .
         */
    }


}
