package com.example.senamit.nanodegreemovieapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.senamit.nanodegreemovieapp.Data.MovieContract;
import com.example.senamit.nanodegreemovieapp.Data.MovieContract.*;

/**
 * Created by senamit on 11/1/18.
 */

public class CustomCursorBookmarkMovieAdapter extends RecyclerView.Adapter<CustomCursorBookmarkMovieAdapter.ViewHolder> {

    public static final String LOG_TAG = CustomCursorBookmarkMovieAdapter.class.getSimpleName();

    Context context;
    Cursor mCursor;

    public CustomCursorBookmarkMovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_movie_bookmark_recylcer,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(WishListMovie._ID);
        int movieNameIndex = mCursor.getColumnIndex(WishListMovie.COLUMN_MOVIE_NAME);
        int movieReleaseDateIndex = mCursor.getColumnIndex(WishListMovie.COLUMN_MOVIE_RELEASE_DATE);
        Log.i(LOG_TAG, "inside on bind of bookmark activity");
        mCursor.moveToPosition(position);
//        mCursor.moveToNext();
//        while (mCursor.moveToNext()){
            final int id = mCursor.getInt(idIndex);
            String movieName = mCursor.getString(movieNameIndex);
            String movieReleaseDate = mCursor.getString(movieReleaseDateIndex);
        Log.i(LOG_TAG, "the value of id is"+id);
        Log.i(LOG_TAG, "the value of movie name is"+movieName);

//            holder.itemView.setTag(id);

            holder.txtMovieName.setText(movieName);
            holder.txtMovieReleaseDate.setText(movieReleaseDate);
//        }
    }

    @Override
    public int getItemCount() {
        if (mCursor==null){
            return 0;
        }
        Log.i(LOG_TAG, "the count of cursor is "+mCursor.getCount());
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor cursor){
        if (mCursor==cursor){
            return null;
        }
        Cursor tempCursor= mCursor;
        this.mCursor = cursor;
        if (cursor!=null){
            this.notifyDataSetChanged();
        }
        return tempCursor;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMovieName;
        TextView txtMovieReleaseDate;

        public ViewHolder(View itemView) {
            super(itemView);

            txtMovieName =  itemView.findViewById(R.id.txt_movie_name);
            txtMovieReleaseDate = itemView.findViewById(R.id.txt_movieReleaseDate);
        }
    }
}
