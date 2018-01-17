package com.example.senamit.nanodegreemovieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by senamit on 17/1/18.
 */

public class MovieListViewVideoAdapter extends ArrayAdapter<MovieDetails>{

    public MovieListViewVideoAdapter(Context context, ArrayList<MovieDetails> movie){
        super(context, 0, movie);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        MovieDetails movieDetails = getItem(position);
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_view_item, parent, false);

        }
        TextView txtVideoName = convertView.findViewById(R.id.txtVideoName);
        TextView txtVideoLink = convertView.findViewById(R.id.txtVideoLink);

        txtVideoName.setText(movieDetails.getMovieVideoName());
        txtVideoLink.setText(movieDetails.getMovieVideo());

        return convertView;
    }


}
