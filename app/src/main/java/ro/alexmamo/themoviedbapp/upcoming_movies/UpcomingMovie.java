package ro.alexmamo.themoviedbapp.upcoming_movies;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class UpcomingMovie {
    public int id;
    public String title;
    @SerializedName("poster_path")
    public String posterPath;

    public UpcomingMovie(int id, String title, String posterPath) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}