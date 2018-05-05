package com.nanodegree.ibrahim.popularmovies2.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.nanodegree.ibrahim.popularmovies2.DetailsActivity;
import com.nanodegree.ibrahim.popularmovies2.MainActivity;
import com.nanodegree.ibrahim.popularmovies2.R;
import com.nanodegree.ibrahim.popularmovies2.interfaces.OnItemClickListener;
import com.nanodegree.ibrahim.popularmovies2.model.Videos;

import java.util.ArrayList;

/**
 * Created by ibrahim on 05/05/18.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VediosAdapterViewHolder> {
    private final Context context;
    /*get Movies Class as object inside list to set and get all data from it */
    private static final int ANIMATION_DURATION = 200;

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

        // Within MoviesAdapterViewHolder ///////////////////////////////////////////////////////
        public final TextView mMvideosTypeTextView;
        public final WebView mWebview;


        public VediosAdapterViewHolder(View view) {
            super(view);
            mMvideosTypeTextView = view.findViewById(R.id.tv_video_type);
            mWebview = view.findViewById(R.id.tv_video);

        }

        public void bind(final Videos item, final OnItemClickListener listener) {

            final String yotube ="https://www.youtube.com/watch?v="+item.getKey();
            mMvideosTypeTextView.setText(item.getName());
            https://api.themoviedb.org/3/movie/337167/videos?api_key=fa22ceab3172625817f5b2523e53ecd2
            Log.v(TAG,"yotubeUrl="+yotube);
            WebSettings webSettings = mWebview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            mWebview.getSettings().setBuiltInZoomControls(true); // allow pinch to zooom
            mWebview.getSettings().setDisplayZoomControls(false);
          //  mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

            mWebview.setWebViewClient(new WebViewClient() {
                @SuppressWarnings("deprecation")
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(context, description, Toast.LENGTH_SHORT).show();
                }
                @TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                }
            });

            mWebview .loadUrl(yotube);


        }
    }

}
