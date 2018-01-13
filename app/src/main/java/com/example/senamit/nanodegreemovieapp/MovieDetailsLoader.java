package com.example.senamit.nanodegreemovieapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailsLoader extends AsyncTaskLoader<List<MovieDetails>> {
    private static final String LOG_TAG = MovieDetailsLoader.class.getName();
    private String stringUrl;
    ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<>();
    List<MovieDetails> resultFromHTTP;

    public MovieDetailsLoader(Context context, String stringUrl) {
        super(context);
        this.stringUrl = stringUrl;
    }

    @Override
    protected void onStartLoading() {
        if (resultFromHTTP != null) {
            deliverResult(resultFromHTTP);
        } else {
            super.onForceLoad();
        }

    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    public List<MovieDetails> loadInBackground() {
        if (stringUrl != null && "".equals(stringUrl)) {

            return null;
        }
        try {

            movieDetailsArrayList = QueryUtils.fetchMovieRequest(stringUrl);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieDetailsArrayList;
    }

    @Override
    public void deliverResult(List<MovieDetails> data) {
        resultFromHTTP = data;
        super.deliverResult(data);
    }
}
