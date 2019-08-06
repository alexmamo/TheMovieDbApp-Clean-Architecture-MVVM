package ro.alexmamo.themoviedbapp.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ro.alexmamo.themoviedbapp.movie_details.MovieDetails;
import ro.alexmamo.themoviedbapp.movie_details.play.TrailersApiResponse;
import ro.alexmamo.themoviedbapp.upcoming_movies.UpcomingMoviesApiResponse;

public interface TheMovieDbApi {
    @GET("movie/upcoming")
    Call<UpcomingMoviesApiResponse> getMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("search/movie")
    Call<UpcomingMoviesApiResponse> getSearchedMovies(
            @Query("query") String query,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/videos")
    Call<TrailersApiResponse> getTrailers(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );
}