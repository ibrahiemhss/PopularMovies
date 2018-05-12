package com.nanodegree.ibrahim.popularmovies2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.ibrahim.popularmovies2.DetailsActivity;
import com.nanodegree.ibrahim.popularmovies2.R;
import com.nanodegree.ibrahim.popularmovies2.WebViewActivity;
import com.nanodegree.ibrahim.popularmovies2.interfaces.OnItemClickListener;
import com.nanodegree.ibrahim.popularmovies2.model.Videos;

import java.util.ArrayList;

import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_WEBVIEW_URL;

/**
 *
 * Created by ibrahim on 05/05/18.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VediosAdapterViewHolder> {
    private final Context context;
    /*get Movies Class as object inside list to set and get all data from it */

    private ArrayList<Videos> listVideos;
    private final OnItemClickListener listener;
    private static final String TAG = "VideosAdapter";

    public VideosAdapter(DetailsActivity context, ArrayList<Videos> videos, OnItemClickListener listener) {
        this.listVideos = videos;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public VideosAdapter.VediosAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.videos_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new VideosAdapter.VediosAdapterViewHolder(view);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the
     *                 contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull VideosAdapter.VediosAdapterViewHolder holder, int position) {
        holder.bind(listVideos.get(position), listener);

    }

    /**
     * @return The number of items available in our Movie
     */
    @Override
    public int getItemCount() {
        if (null == listVideos) return 0;
        return listVideos.size();
    }

    /**
     * @param videosArrayList The Arraylist value that come from AsyncTask
     */
    public void updateVideos(ArrayList<Videos> videosArrayList) {
        this.listVideos = videosArrayList;
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class VediosAdapterViewHolder extends RecyclerView.ViewHolder {

        // Within VediosAdapterViewHolder ///////////////////////////////////////////////////////
        private final TextView mMvideosTypeTextView;


        private VediosAdapterViewHolder(View view) {
            super(view);
            mMvideosTypeTextView = view.findViewById(R.id.tv_video_type);

        }

        @SuppressLint("SetJavaScriptEnabled")
        public void bind(final Videos item, final OnItemClickListener listener) {

            final String yotubeUrl ="https://www.youtube.com/watch?v="+item.getKey();
            mMvideosTypeTextView.setText(item.getType());
            Log.v(TAG,"yotubeUrl="+yotubeUrl);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick();

                    // probably set ImageView.scaleType to `fitXY` so it stretches
        /*set on click for image view with intent PutExtra data from current position*/
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(EXTRA_WEBVIEW_URL,yotubeUrl);
                    context.startActivity(intent);


                }
            });

        }
    }

}
