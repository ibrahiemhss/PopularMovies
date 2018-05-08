package com.nanodegree.ibrahim.popularmovies2.utilities;


import com.nanodegree.ibrahim.popularmovies2.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 * Created by *i*brahim on 28/04/18.
 */
class OpenMoviesUtils {

    public static ArrayList<Movies> getMovies(String moviesJsonStr)

            throws JSONException {
        //  final String TOTAL_ٌُPAGES = "total_pages";
        Movies movies;
        ArrayList<Movies> moviesArrayList = new ArrayList<>();

        /* information. Each Movies info is an element of the "results" array */
        final String RESULTS = "results";


                       /*all  childrens  objects in "results" array */
       final String ID = "id";
        final String VIDEO = "video";
        final String VOTE_AVERAGE = "vote_average";
        final String TITLE = "title";
        final String POPULARITY = "popularity";
        final String POSTER_PATHE = "poster_path";
        final String ORIGINAL_LANG = "original_language";
        final String ORIGINAL_TILTE = "original_title";
        final String GENRE_IDS = "genre_ids";
        final String BACKDROP_PATH = "backdrop_path";
        final String ADULT = "adult";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";

        /* String array to hold each ellements String */
        JSONObject forecastJson = new JSONObject(moviesJsonStr);
        //get all data inside JSONObject forecastJson
        JSONArray moviesArrayResults = forecastJson.getJSONArray(RESULTS);
        //get all position of array-->  parsedMoviesData that come from JSONArray-->  moviesArrayResults
        for (int i = 0; i < moviesArrayResults.length(); i++) {
            /* These are the values that will be collected */

            movies = new Movies();
              long id;
            boolean isVideo;
            long voteAverage;
            String title;
            long popularity;
            String posterPath;
            String originalLanguage;
            String originalTitle;
            StringBuilder genders;
            String backdropPath;
            boolean isAdult;
            String overview;
            String releasDate;


                       /* Get the JSON object representing the results */
            JSONObject objResult = moviesArrayResults.getJSONObject(i);
                        /* Get the JSON object representing the -->...vote_count... from--> JSONObject(results)*/
             id = objResult.getLong(ID);
                                        /* Get the JSON object representing the -->...video ... from--> JSONObject(results)*/
            isVideo = objResult.getBoolean(VIDEO);
                        /* Get the JSON object representing the--> ...vote_average... from -->JSONObject(results)*/
            voteAverage = objResult.getLong(VOTE_AVERAGE);
                       /* Get the JSON object representing the -->...title... from--> JSONObject(results)*/
            title = objResult.getString(TITLE);
                        /* Get the JSON object representing the--> ...popularity... from -->JSONObject(results)*/
            popularity = objResult.getLong(POPULARITY);
                        /* Get the JSON object representing the -->...poster_path... from -->JSONObject(results)*/
            posterPath = objResult.getString(POSTER_PATHE);
                        /* Get the JSON object representing the--> ....original_language.... from--> JSONObject(results)*/
            originalLanguage = objResult.getString(ORIGINAL_LANG);
                       /* Get the JSON object representing the -->....original_title.... from--> JSONObject(results)*/
            originalTitle = objResult.getString(ORIGINAL_TILTE);
                        /*make new Arraylist to put items of list genders inside it*/
            //noinspection MismatchedQueryAndUpdateOfCollection
            ArrayList<String> arrayListGeders = new ArrayList<>();
                        /* Get the JSONArray representing the -->....genre_ids... from--> JSONObject(results)*/
            JSONArray moviesArrayGendre = objResult.getJSONArray(GENRE_IDS);
            genders = new StringBuilder();
            for (int i2 = 0; i2 < moviesArrayGendre.length(); i2++) {
                             /* add list of moviesArrayGendre inside --> arrayListGeders*/
                arrayListGeders.add(moviesArrayGendre.getString(i2));
                            /* append arrayListGeders in -->StringBulder genders*/
                genders.append(moviesArrayGendre.getString(i2));
                genders.append(",\n");
                //  movies.setGenre_ids(genders.);

            }
                        /* Get the JSON object representing the--> ....backdrop_path.... from--> JSONObject(results)*/
            backdropPath = objResult.getString(BACKDROP_PATH);
                        /* Get the JSON object representing the--> ....adult.... from -->JSONObject(results)*/
            isAdult = objResult.getBoolean(ADULT);
              /* Get the JSON object representing the -->....overview.... from--> JSONObject(results)*/
            overview = objResult.getString(OVERVIEW);
                        /* Get the JSON object representing the -->....release_date.... from--> JSONObject(results)*/
            releasDate = objResult.getString(RELEASE_DATE);

            movies.setId(id);
            movies.setVideo(isVideo);
            movies.setVotAverage(voteAverage);
            movies.setTilte(title);
            movies.setPopularity(popularity);
            movies.setPoster_path(posterPath);
            movies.setOriginal_language(originalLanguage);
            movies.setOriginal_title(originalTitle);
            movies.setGenre_ids(arrayListGeders);
            movies.setBackdrop_path(backdropPath);
            movies.setAdult(isAdult);
            movies.setOverview(overview);
            movies.setRelease_date(releasDate);


            moviesArrayList.add(movies);

        }

        return moviesArrayList;
    }
}
