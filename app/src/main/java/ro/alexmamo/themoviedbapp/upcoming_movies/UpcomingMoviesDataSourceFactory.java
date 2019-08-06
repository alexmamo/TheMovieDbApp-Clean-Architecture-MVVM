package ro.alexmamo.themoviedbapp.upcoming_movies;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class UpcomingMoviesDataSourceFactory extends DataSource.Factory<Integer, UpcomingMovie> {
    private MutableLiveData<PageKeyedDataSource<Integer, UpcomingMovie>> liveData = new MutableLiveData<>();
    private String searchText;

    UpcomingMoviesDataSourceFactory(String searchText) {
        this.searchText = searchText;
    }

    @NonNull
    @Override
    public DataSource<Integer, UpcomingMovie> create() {
        UpcomingMoviesDataSource upcomingMoviesDataSource = new UpcomingMoviesDataSource(searchText);
        liveData.postValue(upcomingMoviesDataSource);
        return upcomingMoviesDataSource;
    }
}