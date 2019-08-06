package ro.alexmamo.themoviedbapp.movie_details.play;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrailersApiResponse {
    @SerializedName("results")
    public List<Trailer> trailers = new ArrayList<>();
}