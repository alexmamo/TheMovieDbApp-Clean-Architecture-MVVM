package ro.alexmamo.themoviedbapp.upcoming_movies;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.alexmamo.themoviedbapp.network.RetrofitClient;
import ro.alexmamo.themoviedbapp.network.TheMovieDbApi;

import static ro.alexmamo.themoviedbapp.utils.Constants.APY_KEY;
import static ro.alexmamo.themoviedbapp.utils.Constants.FIRST_PAGE;
import static ro.alexmamo.themoviedbapp.utils.HelperClass.createNewCall;
import static ro.alexmamo.themoviedbapp.utils.HelperClass.logMessage;

public class UpcomingMoviesDataSource extends PageKeyedDataSource<Integer, UpcomingMovie> {
    private TheMovieDbApi api;
    private String searchText;

    UpcomingMoviesDataSource(String searchText) {
        this.searchText = searchText;
        api = RetrofitClient.getInstance().getTheMovieDbApi();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, UpcomingMovie> callback) {
        Call<UpcomingMoviesApiResponse> call = getTheMovieDbApiResponseCall(FIRST_PAGE);
        Callback<UpcomingMoviesApiResponse> upcomingMoviesCallback = new Callback<UpcomingMoviesApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpcomingMoviesApiResponse> call, @NonNull Response<UpcomingMoviesApiResponse> response) {
                UpcomingMoviesApiResponse upcomingMoviesApiResponse = response.body();
                if (upcomingMoviesApiResponse != null) {
                    callback.onResult(upcomingMoviesApiResponse.upcomingMovies, null, FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpcomingMoviesApiResponse> call, @NonNull Throwable t) {
                logMessage(t.getMessage());
                createNewCall(call);
            }
        };
        call.enqueue(upcomingMoviesCallback);
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, UpcomingMovie> callback) {}

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, UpcomingMovie> callback) {
        Call<UpcomingMoviesApiResponse> call = getTheMovieDbApiResponseCall(params.key);
        Callback<UpcomingMoviesApiResponse> upcomingMoviesCallback = new Callback<UpcomingMoviesApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpcomingMoviesApiResponse> call, @NonNull Response<UpcomingMoviesApiResponse> response) {
                UpcomingMoviesApiResponse upcomingMoviesApiResponse = response.body();
                if (upcomingMoviesApiResponse != null) {
                    Integer key = (params.key <= upcomingMoviesApiResponse.totalPages) ? params.key + 1 : null;
                    callback.onResult(upcomingMoviesApiResponse.upcomingMovies, key);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpcomingMoviesApiResponse> call, @NonNull Throwable t) {
                logMessage(t.getMessage());
                createNewCall(call);
            }
        };
        call.enqueue(upcomingMoviesCallback);
    }

    private Call<UpcomingMoviesApiResponse> getTheMovieDbApiResponseCall(int pageNumber) {
        return (searchText == null) ? api.getMovies(APY_KEY, pageNumber) : api.getSearchedMovies(searchText, APY_KEY, pageNumber);
    }
}