package com.nanodegree.ibrahim.popularmovies2.interfaces;


public interface AsyncTaskCompleteListener<T>
{
    /**
     * Invoked when the AsyncTask has completed its execution.
     * @param result The resulting object from the AsyncTask.
     */
    void onTaskComplete(T result);
}