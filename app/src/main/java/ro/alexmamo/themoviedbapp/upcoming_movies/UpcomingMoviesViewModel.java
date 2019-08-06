package ro.alexmamo.themoviedbapp.upcoming_movies;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import static ro.alexmamo.themoviedbapp.utils.Constants.RECORDS_PER_PAGE;

public class UpcomingMoviesViewModel extends AndroidViewModel {
    private PagedList.Config config;
    private UpcomingMoviesDataSourceFactory sourceFactory;
    LiveData<PagedList<UpcomingMovie>> pagedListLiveData;

    public UpcomingMoviesViewModel(Application application) {
        super(application);

        config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(RECORDS_PER_PAGE)
                .build();

        sourceFactory = new UpcomingMoviesDataSourceFactory(null);
        pagedListLiveData = new LivePagedListBuilder<>(sourceFactory, config).build();
    }

    LiveData<PagedList<UpcomingMovie>> getMoviesPagedListLiveData() {
        return pagedListLiveData;
    }

    void replaceSubscription(LifecycleOwner lifecycleOwner, String searchText) {
        pagedListLiveData.removeObservers(lifecycleOwner);
        if (searchText == null) {
            sourceFactory = new UpcomingMoviesDataSourceFactory(null);
        } else {
            sourceFactory = new UpcomingMoviesDataSourceFactory(searchText);
        }
        pagedListLiveData = new LivePagedListBuilder<>(sourceFactory, config).build();
    }
}