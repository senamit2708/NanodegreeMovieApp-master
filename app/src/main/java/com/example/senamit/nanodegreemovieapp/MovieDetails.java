package com.example.senamit.nanodegreemovieapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class MovieDetails implements Parcelable {

    private String movieName;
    private String movieReleaseDate;
    private String movieRating;
    private String movieOverView;
    private String movieImageUrl;
    private Bitmap bitmap;

    public MovieDetails(String movieName, String movieReleaseDate, String movieRating, String movieOverView, Bitmap bitmap) {
        this.movieName = movieName;
        this.movieReleaseDate = movieReleaseDate;
        this.movieRating = movieRating;
        this.movieOverView = movieOverView;
        this.bitmap = bitmap;
    }

    public MovieDetails() {
    }

    public MovieDetails(String movieName, String movieReleaseDate, String movieRating, String movieOverView, String movieImageUrl) {
        this.movieName = movieName;
        this.movieReleaseDate = movieReleaseDate;
        this.movieRating = movieRating;
        this.movieOverView = movieOverView;
        this.movieImageUrl = movieImageUrl;
    }

    protected MovieDetails(Parcel in) {
        movieName = in.readString();
        movieReleaseDate = in.readString();
        movieRating = in.readString();
        movieOverView = in.readString();
        movieImageUrl = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };

    public String getMovieName() {
        return movieName;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public String getMovieOverView() {
        return movieOverView;
    }

    public String getMovieImageUrl() {
        return movieImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieName);
        parcel.writeString(movieReleaseDate);
        parcel.writeString(movieRating);
        parcel.writeString(movieOverView);
        parcel.writeString(movieImageUrl);
        parcel.writeParcelable(bitmap, i);
    }

}
