package com.example.senamit.nanodegreemovieapp.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by senamit on 2/1/18.
 */

public class MovieContract {

    private MovieContract() {
    }

    public static final String AUTHORITY = "com.example.senamit.nanodegreemovieapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIE_LIST = "MovieList";

    //now inner class for each table
    public static class WishListMovie implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_LIST).build();

        public static final String TABLE_NAME = "MovieList";
        public static final String COLUMN_MOVIE_NAME = "MovieName";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "MovieReleaseDate";
        public static final String COLUMN_MOVIE_POSTER="MoviePoster";
        public static final String COLUMN_MOVIE_RATING="MovieRating";
        public static final String COLUMN_MOVIE_OVERVIEW="MovieOverView";


    }

}
