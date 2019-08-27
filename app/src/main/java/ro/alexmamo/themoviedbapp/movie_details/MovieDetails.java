package ro.alexmamo.themoviedbapp.movie_details;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class MovieDetails {
    public int id;
    String title;
    @SerializedName("original_language")
    String language;
    ArrayList genres;
    public String overview;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("poster_path")
    String posterPath;

    MovieDetails() {}

    public String getTitleAndReleaseDate(MovieDetails movieDetails) {
        return movieDetails.title + " (" + movieDetails.releaseDate.substring(0, 4) + ")";
    }

    public String getLanguage(MovieDetails movieDetails){
        String language = movieDetails.language;
        return  "Language: " + language.substring(0,1).toUpperCase() + language.substring(1);
    }

    public String getGenres(MovieDetails movieDetails) {
        ArrayList genres = movieDetails.genres;
        StringBuilder genresStringBuilder = new StringBuilder();
        String separator = ", ";
        for (int i = 0 ; i < genres.size(); i++) {
            LinkedTreeMap treeMap = (LinkedTreeMap) genres.get(i);
            String name = (String) treeMap.get("name");
            if (name != null) {
                if (i == genres.size() - 1) {
                    separator = ".";
                }
                genresStringBuilder.append(name).append(separator);
            }
        }
        return "Genres: " + genresStringBuilder;
    }
}