package com.nanodegree.ibrahim.popularmovies2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.nanodegree.ibrahim.popularmovies2.DetailsActivity;
import com.nanodegree.ibrahim.popularmovies2.MainActivity;
import com.nanodegree.ibrahim.popularmovies2.R;
import com.nanodegree.ibrahim.popularmovies2.interfaces.OnItemClickListener;
import com.nanodegree.ibrahim.popularmovies2.model.Movies;

import java.util.ArrayList;

import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_ID;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_OVERVIEW;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_RATE;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_TITLE;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_URL;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.EXTRA_YEAR;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.IMAGE_URL;
import static com.nanodegree.ibrahim.popularmovies2.data.Contract.W500;

/**
 * Created by ibrahim on 29/04/18.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {
    private final Context context;
    /*get Movies Class as object inside list to set and get all data from it */
    private ArrayList<Movies> listMovie;
    private final OnItemClickListener listener;

    public MoviesAdapter(MainActivity context, ArrayList<Movies> movies, OnItemClickListener listener) {
        this.listMovie = movies;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MoviesAdapterViewHolder(view);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the
     *                 contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MoviesAdapterViewHolder holder, int position) {
        holder.bind(listMovie.get(position), listener);

    }

    /**
     * @return The number of items available in our Movie
     */
    @Override
    public int getItemCount() {
        if (null == listMovie) return 0;
        return listMovie.size();
    }

    /**
     * @param movie_list The Arraylist value that come from AsyncTask
     */
    public void updateMovies(ArrayList<Movies> movie_list) {
        this.listMovie = movie_list;
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder {

        // Within MoviesAdapterViewHolder ///////////////////////////////////////////////////////
        public final TextView mMovieTextView;
        public final ImageView poster;


        public MoviesAdapterViewHolder(View view) {
            super(view);
            mMovieTextView = view.findViewById(R.id.tv_movie_data);
            poster = view.findViewById(R.id.poster_img);

        }

        public void bind(final Movies item, final OnItemClickListener listener) {

            final String title = item.getTilte();
            mMovieTextView.setText(title);
            final String url = IMAGE_URL.trim() + W500.trim() + item.getPoster_path();
            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_image_blank)
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)// set exact size
                            .fitCenter() // keep memory usage low by fitting into (w x h) [optional]
                    )
                    .into(poster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick();

                    // probably set ImageView.scaleType to `fitXY` so it stretches
        /*set on click for image view with intent PutExtra data from current position*/

                    Intent intent = new Intent(context, DetailsActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString(EXTRA_TITLE, item.getPoster_path());
                    extras.putString(EXTRA_URL, title);
                    extras.putString(EXTRA_ID, String.valueOf(item.getId()));
                    extras.putString(EXTRA_YEAR, String.valueOf(item.getRelease_date()));
                    extras.putString(EXTRA_RATE, String.valueOf(item.getVotAverage()));
                    extras.putString(EXTRA_OVERVIEW, item.getOverview());

                    intent.putExtras(extras);
                    context.startActivity(intent);


                }
            });
        }
    }

}
