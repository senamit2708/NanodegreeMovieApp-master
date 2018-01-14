package com.example.senamit.nanodegreemovieapp.Data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.senamit.nanodegreemovieapp.Data.MovieContract.*;

/**
 * Created by senamit on 8/1/18.
 */

public class MovieContenetProvider extends ContentProvider {

    public static final String LOG_TAG = MovieContenetProvider.class.getSimpleName();

    MovieDBHelper movieDBHelper;
    SQLiteDatabase db;

    public static final int MOVIE_LIST = 100;
    public static final int MOVIE_LIST_ITEM = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE_LIST, MOVIE_LIST);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE_LIST + "/#", MOVIE_LIST_ITEM);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {

        movieDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        db = movieDBHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match) {
            case MOVIE_LIST:
                Log.i(LOG_TAG, "inside table query method full");
                retCursor = db.query(WishListMovie.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_LIST_ITEM:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                retCursor = db.query(WishListMovie.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new android.database.SQLException("uri is bad" + uri);

        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        Log.i(LOG_TAG, "inside insert method");
        db = movieDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        long id;
        Uri retUri = null;
        switch (match) {
            case MOVIE_LIST:
                id = db.insert(WishListMovie.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    Log.i(LOG_TAG, "inside the insertion of new data of table");
                    retUri = ContentUris.withAppendedId(WishListMovie.CONTENT_URI, id);
                } else {
                    Log.i(LOG_TAG, "inside the exception block of insertion of new data");
//                    throw  new android.database.SQLException("unable to insert data "+uri);
                }

                break;
            case MOVIE_LIST_ITEM:
                break;
            default:
                throw new android.database.SQLException("Uri is bad" + uri);
        }
        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
