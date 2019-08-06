package ro.alexmamo.themoviedbapp.upcoming_movies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UpcomingMoviesApiResponse {
    @SerializedName("results")
    List<UpcomingMovie> upcomingMovies = new ArrayList<>();
    @SerializedName("total_pages")
    int totalPages;
}