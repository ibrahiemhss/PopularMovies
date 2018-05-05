package com.nanodegree.ibrahim.popularmovies2.data;


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

    //the url of value of image view
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static final String W185 = "w185";
    public static final String W500 = "w500";



}
