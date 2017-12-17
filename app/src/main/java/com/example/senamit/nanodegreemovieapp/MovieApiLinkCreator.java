package com.example.senamit.nanodegreemovieapp;

import android.net.Uri;
import android.util.Log;

/**
 * Created by senamit on 13/12/17.
 */

public class MovieApiLinkCreator {

    private static String POPULAR_JSON_DATA = "https://api.themoviedb.org/3/movie/popular?page=1&language=en-US";
    private static String NOWPLAYING_JSON_DATA = "https://api.themoviedb.org/3/movie/now_playing?page=5&language=en-US";
    private static String UPCOMING_JSON_DATA = "https://api.themoviedb.org/3/movie/upcoming?page=2&language=en-US";
    private static String TOP_RATED_JSON_DATA = "https://api.themoviedb.org/3/movie/top_rated?page=1&language=en-US";
    private static String APIKEY = "api_key";
    private static String KEY = "";

    public static String favrtMovieUrl1 = Uri.parse(POPULAR_JSON_DATA).buildUpon().appendQueryParameter(APIKEY, KEY).build().toString();

    public static String favrtMovieUrl2 = Uri.parse(TOP_RATED_JSON_DATA).buildUpon().appendQueryParameter(APIKEY, KEY).build().toString();

    public static String favrtMovieUrl3 = Uri.parse(NOWPLAYING_JSON_DATA).buildUpon().appendQueryParameter(APIKEY, KEY).build().toString();

    public static String favrtMovieUrl4 = Uri.parse(UPCOMING_JSON_DATA).buildUpon().appendQueryParameter(APIKEY, KEY).build().toString();


}
