package com.nanodegree.ibrahim.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 *
 * Created by ibrahim on 29/04/18.
 */

public class Movies implements Parcelable {

    private static final String TAG = "Movies";
    private boolean video;
    private long votAverage;
    private String tilte;
    private long popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private ArrayList<String> genre_ids;
    private long id;
    private String backdrop_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private boolean favorite;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite() {
        this.favorite = false;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setGenre_ids(ArrayList<String> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    @SuppressWarnings("unused")
    public static Creator<Movies> getCREATOR() {
        return CREATOR;
    }


    /*After implementing the `Parcelable` interface, we need to create the
    `Parcelable.Creator<Movies> CREATOR` constant for our class;
     Notice how it has our class specified as its type.*/
    private static final Creator<Movies> CREATOR = new Creator<Movies>() {


        /* This simply calls our new constructor (typically private) and
        * passes along the  `Parcel`, and then returns the new object!*/
        @Override
        public Movies createFromParcel(Parcel in) {
            Log.v(TAG, "createbeParcelaple");

            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public Movies() {
    }

    /* Using the `in` variable, we can retrieve the values that
     we originally wrote into the `Parcel`.  This constructor is usually
     private so that only the `CREATOR` field can access.*/
    private Movies(Parcel in) {
        video = in.readByte() != 0;
        votAverage = in.readLong();
        tilte = in.readString();
        popularity = in.readLong();
        poster_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        genre_ids = in.createStringArrayList();
        id = in.readLong();
        backdrop_path = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        release_date = in.readString();
    }

// --Commented out by Inspection START (03/05/18 01:40 ص):
//    // The `Parcel` class has methods defined to help you save all of your values.
//    public static Creator<Movies> getCREATOR() {
//        return CREATOR;
//    }
// --Commented out by Inspection STOP (03/05/18 01:40 ص)

    /*override for setter and getter to all variables to use it out side
    to set values from json in OpenMoviesUtil class
    and get values in MovieAdapter Class
     */
    public long getVotAverage() {
        return votAverage;
    }

    public void setVotAverage(long votAverage) {
        this.votAverage = votAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    // In the vast majority of cases you can simply return 0 for this.
    @Override
    public int describeContents() {
        Log.v(TAG, "describeParcelaple");
        return 0;
    }

    // write the values we want to save to the `Parcel`.
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Log.v(TAG, "writeParcelaple");

        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeLong(votAverage);
        parcel.writeString(tilte);
        parcel.writeLong(popularity);
        parcel.writeString(poster_path);
        parcel.writeString(original_language);
        parcel.writeString(original_title);
        parcel.writeStringList(genre_ids);
        parcel.writeLong(id);
        parcel.writeString(backdrop_path);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(overview);
        parcel.writeString(release_date);
    }
}
