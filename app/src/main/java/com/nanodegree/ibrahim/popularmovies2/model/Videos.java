package com.nanodegree.ibrahim.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by ibrahim on 05/05/18.
 */

public class Videos implements Parcelable {
    private  String key;
    private  String name;
    private String type;
    private String size;
    private String site;
public Videos(){

}
    private Videos(Parcel in) {
        key = in.readString();
        name = in.readString();
        type = in.readString();
        size = in.readString();
        site = in.readString();
    }

    public static final Creator<Videos> CREATOR = new Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };



    public void setSite(String site) {
        this.site = site;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSize(String size) {
        this.size = size;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(size);
        parcel.writeString(site);
    }
}
