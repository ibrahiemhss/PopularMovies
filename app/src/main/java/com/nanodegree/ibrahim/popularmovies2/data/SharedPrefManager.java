package com.nanodegree.ibrahim.popularmovies2.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ibrahim on 30/12/17.
 * SharedPrefManager will save the value of selected sort of show that will base in query url
 *
 * @see <a href="https://github.com/ibrahiemhss/Mashaweer-master/blob/master/app/src/main/java/com/mashaweer/ibrahim/mashaweer/data/SharedPrefManager.java"">https://github.com</a>
 */
public class SharedPrefManager {
    private static final String PREF_SORT_SELLECTED = "pref_selected";

    private static final String SHARED_PREF_NAME = "save_contents";
    private static SharedPrefManager mInstance;
    private final SharedPreferences pref;

    private SharedPrefManager(Context context) {
        pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);

        }
        return mInstance;
    }

    public String getPrefUrlSellected() {
        return pref.getString(PREF_SORT_SELLECTED, null);

    }

    public void setPrefUrlSellected(String sellected) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_SORT_SELLECTED, sellected);
        editor.apply();
        editor.commit();

    }
}