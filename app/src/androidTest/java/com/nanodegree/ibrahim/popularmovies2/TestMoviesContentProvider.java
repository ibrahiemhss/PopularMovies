package com.nanodegree.ibrahim.popularmovies2;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.nanodegree.ibrahim.popularmovies2.data.Contract;
import com.nanodegree.ibrahim.popularmovies2.data.MovieHelper;
import com.nanodegree.ibrahim.popularmovies2.data.MoviesContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 *
 * Created by ibrahim on 09/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class TestMoviesContentProvider {
    private static final Uri TEST_MOVIES = Contract.MoviesEntry.CONTENT_URI;
    // Content URI for a single task with id = 1
    private static final Uri TEST_MOVIES_WITH_ID = TEST_MOVIES.buildUpon().appendPath("1").build();
    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void setUp() {
        MovieHelper dbHelper = new MovieHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(Contract.MoviesEntry.TABLE_NAME, null, null);
    }

    @Test
    public void testProviderRegistry() {


        String packageName = mContext.getPackageName();
        String taskProviderClassName = MoviesContentProvider.class.getName();
        ComponentName componentName = new ComponentName(packageName, taskProviderClassName);

        try {


            PackageManager pm = mContext.getPackageManager();

            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            String actualAuthority = providerInfo.authority;

            String incorrectAuthority =
                    "Error: TaskContentProvider registered with authority: " + actualAuthority +
                            " instead of expected authority: " + packageName;
            assertEquals(incorrectAuthority,
                    actualAuthority,
                    packageName);

        } catch (PackageManager.NameNotFoundException e) {
            String providerNotRegisteredAtAll =
                    "Error: TaskContentProvider not registered at " + mContext.getPackageName();

            fail(providerNotRegisteredAtAll);
        }
    }

    @Test
    public void testUriMatcher() {

        UriMatcher testMatcher = MoviesContentProvider.buildUriMatcher();

        String tasksUriDoesNotMatch = "Error: The TASKS URI was matched incorrectly.";
        int actualTasksMatchCode = testMatcher.match(TEST_MOVIES);
        int expectedTasksMatchCode = MoviesContentProvider.MOVIES;
        assertEquals(tasksUriDoesNotMatch,
                actualTasksMatchCode,
                expectedTasksMatchCode);

        String taskWithIdDoesNotMatch =
                "Error: The TASK_WITH_ID URI was matched incorrectly.";
        int actualTaskWithIdCode = testMatcher.match(TEST_MOVIES_WITH_ID);
        int expectedTaskWithIdCode = MoviesContentProvider.MOVIES_WITH_ID;
        assertEquals(taskWithIdDoesNotMatch,
                actualTaskWithIdCode,
                expectedTaskWithIdCode);
    }


    @Test
    public void testInsert() {

        ContentValues testMoviesValues = new ContentValues();
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_OVERVIEW, "Test overview");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_MOVIES_ID, 1);
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_POSTER_PATH, "movie path");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_RELEASE_DATE, "14:5:1900");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_TITLE, "my movie");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_VOTE_AVERAGE, 1);


        TestUtilities.TestContentObserver taskObserver = TestUtilities.getTestContentObserver();

        ContentResolver contentResolver = mContext.getContentResolver();

        contentResolver.registerContentObserver(
                Contract.MoviesEntry.CONTENT_URI,
                true,
                taskObserver);


        Uri uri = contentResolver.insert(Contract.MoviesEntry.CONTENT_URI, testMoviesValues);


        Uri expectedUri = ContentUris.withAppendedId(Contract.MoviesEntry.CONTENT_URI, 1);

        String insertProviderFailed = "Unable to insert item through Provider";
        assertEquals(insertProviderFailed, uri, expectedUri);


        taskObserver.waitForNotificationOrFail();


        contentResolver.unregisterContentObserver(taskObserver);
    }


    @Test
    public void testQuery() {

        MovieHelper dbHelper = new MovieHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues testMoviesValues = new ContentValues();
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_OVERVIEW, "Test overview");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_MOVIES_ID, 1);
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_POSTER_PATH, "movie path");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_RELEASE_DATE, "14:5:1900");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_TITLE, "my movie");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_VOTE_AVERAGE, 1);

        long taskRowId = database.insert(
                Contract.MoviesEntry.TABLE_NAME,
                null,
                testMoviesValues);

        String insertFailed = "Unable to insert directly into the database";
        assertTrue(insertFailed, taskRowId != -1);

        database.close();

        Cursor taskCursor = mContext.getContentResolver().query(
                Contract.MoviesEntry.CONTENT_URI,
                null,
                null,
                null,
                null);


        String queryFailed = "Query failed to return a valid Cursor";
        assertTrue(queryFailed, taskCursor != null);

        taskCursor.close();
    }

    @Test
    public void testDelete() {
        MovieHelper helper = new MovieHelper(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues testMoviesValues = new ContentValues();
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_OVERVIEW, "Test overview");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_MOVIES_ID, 1);
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_POSTER_PATH, "movie path");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_RELEASE_DATE, "14:5:1900");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_TITLE, "my movie");
        testMoviesValues.put(Contract.MoviesEntry.COLUMN_VOTE_AVERAGE, 1);

        long taskRowId = database.insert(
                Contract.MoviesEntry.TABLE_NAME,
                null,
                testMoviesValues);
        database.close();

        String insertFailed = "Unable to insert into the database";
        assertTrue(insertFailed, taskRowId != -1);


        TestUtilities.TestContentObserver taskObserver = TestUtilities.getTestContentObserver();

        ContentResolver contentResolver = mContext.getContentResolver();

        contentResolver.registerContentObserver(
                Contract.MoviesEntry.CONTENT_URI,
                true,
                taskObserver);


        Uri uriToDelete = Contract.MoviesEntry.CONTENT_URI.buildUpon().appendPath("1").build();
        int tasksDeleted = contentResolver.delete(uriToDelete, null, null);

        String deleteFailed = "Unable to delete item in the database";
        assertTrue(deleteFailed, tasksDeleted != 0);

        taskObserver.waitForNotificationOrFail();

        contentResolver.unregisterContentObserver(taskObserver);
    }
}
