package com.nanodegree.ibrahim.popularmovies2.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nanodegree.ibrahim.popularmovies2.data.Contract.MoviesEntry;

/**
 *
 * Created by ibrahim on 09/05/18.
 */

public class MovieHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "moviesDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;

    public MovieHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE " + MoviesEntry.TABLE_NAME + " (" +
                MoviesEntry.COLUMN_MOVIES_ID + " INTEGER PRIMARY KEY, " +
                MoviesEntry.COLUMN_TITLE + " VRCHAR(11) NOT NULL, " +
                MoviesEntry.COLUMN_VOTE_AVERAGE + " VRCHAR(11) NOT NULL, " +
                MoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_RELEASE_DATE + "  VRCHAR(11) NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
/** @param id The String will come from DetailsActivity
*/
    public boolean verification(String id) throws SQLException {
        int count = -1;
        Cursor c = null;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String query = "SELECT COUNT(*) FROM "
                    + MoviesEntry.TABLE_NAME + " WHERE " + MoviesEntry.COLUMN_MOVIES_ID + " = ?";
            c = db.rawQuery(query, new String[]{id});
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            return count > 0;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }
}
