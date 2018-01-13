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
    public static String MOVIE_DETAILS_JSON_DATA =" https://api.themoviedb.org/3/movie/";
    public static String APIKEY = "api_key";
    public static String KEY = "f6fc8d8e4043fefdfe43c153dd429479";
    public static String LANGUAGE="language";
    public static String LANGUAGEVALUE="en-US";

    public static String favrtMovieUrl1 = Uri.parse(POPULAR_JSON_DATA).buildUpon().appendQueryParameter(APIKEY, KEY).build().toString();

    public static String favrtMovieUrl2 = Uri.parse(TOP_RATED_JSON_DATA).buildUpon().appendQueryParameter(APIKEY, KEY).build().toString();

    public static String favrtMovieUrl3 = Uri.parse(NOWPLAYING_JSON_DATA).buildUpon().appendQueryParameter(APIKEY, KEY).build().toString();

    public static String favrtMovieUrl4 = Uri.parse(UPCOMING_JSON_DATA).buildUpon().appendQueryParameter(APIKEY, KEY).build().toString();

}
