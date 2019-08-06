package ro.alexmamo.themoviedbapp.movie_details;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.alexmamo.themoviedbapp.network.TheMovieDbApi;

import static ro.alexmamo.themoviedbapp.utils.Constants.APY_KEY;
import static ro.alexmamo.themoviedbapp.utils.HelperClass.createNewCall;
import static ro.alexmamo.themoviedbapp.utils.HelperClass.logMessage;

@Singleton
public class MovieDetailsRepository {
    private TheMovieDbApi api;

    @Inject
    MovieDetailsRepository(TheMovieDbApi api) {
        this.api = api;
    }

    MutableLiveData<MovieDetails> addMovieDetailsToLiveData(int movieId) {
        MutableLiveData<MovieDetails> movieDetailsMutableLiveData = new MutableLiveData<>();
        Call<MovieDetails> call = api.getMovieDetails(movieId, APY_KEY);
        Callback<MovieDetails> movieDetailsCallback = new Callback<MovieDetails>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetails> call, @NonNull Response<MovieDetails> response) {
                MovieDetails movieDetails = response.body();
                if (movieDetails != null) {
                    movieDetailsMutableLiveData.setValue(movieDetails);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetails> call, @NonNull Throwable t) {
                logMessage(t.getMessage());
                createNewCall(call);
            }
        };
        call.enqueue(movieDetailsCallback);
        return movieDetailsMutableLiveData;
    }
}