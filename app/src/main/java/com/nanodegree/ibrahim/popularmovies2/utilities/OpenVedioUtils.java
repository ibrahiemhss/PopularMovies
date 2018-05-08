package com.nanodegree.ibrahim.popularmovies2.utilities;

import com.nanodegree.ibrahim.popularmovies2.model.Videos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 * Created by ibrahim on 05/05/18.
 */

class OpenVedioUtils {

    public static ArrayList<Videos> getVideos(String moviesJsonStr)

            throws JSONException {
        //  final String TOTAL_ٌُPAGES = "total_pages";
        Videos videos;
        ArrayList<Videos> videossArrayListt = new ArrayList<>();

        /* information. Each Videos info is an element of the "results" array */
        final String RESULTS = "results";


                       /*all  childrens  objects in "results" array */
        final String KEY = "key";
        final String NAME = "name";
        final String SITE = "site";
        final String SIZE = "size";
        final String TYPE = "type";

        /* String array to hold each ellements String */
        JSONObject videosJson = new JSONObject(moviesJsonStr);
        //get all data inside JSONObject videosJson
        JSONArray VideosArrayResults = videosJson.getJSONArray(RESULTS);
        //get all position of array-->  parsed videos Data that come from JSONArray-->  VideosArrayResults
        for (int i = 0; i < VideosArrayResults.length(); i++) {
            /* These are the values that will be collected */

            videos = new Videos();

            String key;
            String name;
            String site;
            String size;
            String type;

                       /* Get the JSON object representing the results */
            JSONObject objResult = VideosArrayResults.getJSONObject(i);
                        /* Get the JSON object representing the -->...key... from--> JSONObject(results)*/
            key = objResult.optString(KEY);
                        /* Get the JSON object representing the -->...name... from -->JSONObject(results)*/
            name = objResult.optString(NAME);
                        /* Get the JSON object representing the--> ....site.... from--> JSONObject(results)*/
            site = objResult.optString(SITE);
                       /* Get the JSON object representing the -->....size.... from--> JSONObject(results)*/
            size = objResult.optString(SIZE);
                        /* Get the JSON object representing the -->....type.... from--> JSONObject(results)*/
            type = objResult.optString(TYPE);

            videos.setKey(key);
            videos.setName(name);
            videos.setSite(site);
            videos.setSize(size);
            videos.setType(type);


            videossArrayListt.add(videos);

        }

        return videossArrayListt;
    }
}


