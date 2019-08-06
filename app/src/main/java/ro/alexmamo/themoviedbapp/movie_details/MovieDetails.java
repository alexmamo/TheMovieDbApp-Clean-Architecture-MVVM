package ro.alexmamo.themoviedbapp.movie_details;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieDetails {
    public int id;
    String title;
    @SerializedName("original_language")
    String language;
    ArrayList genres;
    String overview;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("poster_path")
    String posterPath;

    MovieDetails() {}
}