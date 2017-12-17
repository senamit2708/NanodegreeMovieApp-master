package com.example.senamit.nanodegreemovieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.ViewHolder> {

    private static final String LOG_TAG = MovieDetailAdapter.class.getSimpleName();
    private final ListItemClickListener onClickListener;
    Context context;
    List<MovieDetails> movieDetailsList;

    public MovieDetailAdapter(List<MovieDetails> movieDetailsList, ListItemClickListener onClickListener) {
        this.movieDetailsList = movieDetailsList;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.movieName.setText(movieDetailsList.get(position).getMovieName());
        holder.movieReleaseDate.setText(movieDetailsList.get(position).getMovieReleaseDate());
        Picasso.with(context).load(movieDetailsList.get(position).getMovieImageUrl()).into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movieName;
        TextView movieReleaseDate;
        ImageView movieImage;

        public ViewHolder(View itemView) {
            super(itemView);
            movieName = (TextView) itemView.findViewById(R.id.txt_movie_name);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.txt_movieReleaseDate);
            movieImage = (ImageView) itemView.findViewById(R.id.img_movieImage);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clikedItemIndex = getAdapterPosition();
            onClickListener.onListItemClick(clikedItemIndex, movieDetailsList.get(clikedItemIndex));
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clikcedItemIndex, MovieDetails movieDetail);
    }
}
