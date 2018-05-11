package com.nanodegree.ibrahim.popularmovies2.utilities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.nanodegree.ibrahim.popularmovies2.data.Contract;
import com.nanodegree.ibrahim.popularmovies2.model.Movies;

import java.util.ArrayList;

/**
 *
 * Created by ibrahim on 11/05/18.
 */

class OpenFavoriteUtils {
    private static final String TAG = "OpenFavoriteUtils";

    public static ArrayList<Movies> getMovies(Context context)

    {
        Movies movies;
        ArrayList<Movies> moviesArrayList = new ArrayList<>();
                 /* get the ContentProvider URI */
        Uri uri = Contract.MoviesEntry.CONTENT_URI;
                /* Perform the ContentProvider query */
        Cursor c = context.getContentResolver().query(uri,
                /* Columns; leaving this null returns every column in the table */
                null,
               /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Sort order to return in Cursor */
                null);

                 /*make sure if curser not null to bypass the mistake */
        if (c != null) {
                /*start cursor reading and move from column to other to find all data inside table*/
            while (c.moveToNext()) {
                movies = new Movies();
                /*get all value by cursor while moving by get its column name and get value inside it*/
                long id = c.getLong(c.getColumnIndexOrThrow(Contract.MoviesEntry.COLUMN_MOVIES_ID));
                String title = c.getString(c.getColumnIndexOrThrow(Contract.MoviesEntry.COLUMN_TITLE));
                String poster = c.getString(c.getColumnIndexOrThrow(Contract.MoviesEntry.COLUMN_POSTER_PATH));
                String overview = c.getString(c.getColumnIndexOrThrow(Contract.MoviesEntry.COLUMN_OVERVIEW));
                String date = c.getString(c.getColumnIndexOrThrow(Contract.MoviesEntry.COLUMN_RELEASE_DATE));
                long overage = c.getLong(c.getColumnIndexOrThrow(Contract.MoviesEntry.COLUMN_VOTE_AVERAGE));
                /*while cursor movement will get value of every column this value will save inside all movie object from Movies Class*/
                movies.setId(id);
                movies.setTilte(title);
                movies.setPoster_path(poster);
                movies.setOverview(overview);
                movies.setRelease_date(date);
                movies.setVotAverage(overage);
                /*add all new value of movie object to moviesArrayList*/
                moviesArrayList.add(movies);

                Log.i(TAG, "FetchMovies\ntitle\n" + title + "\nposter:" + poster + "\noverview:" + overview + "\ndate:" + date + "\noverage:" + overage);
            }
            c.close();
        }


        return moviesArrayList;
    }
}
