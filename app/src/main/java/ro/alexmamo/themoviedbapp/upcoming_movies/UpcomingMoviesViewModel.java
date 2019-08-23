package ro.alexmamo.themoviedbapp.upcoming_movies;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import javax.inject.Inject;

public class UpcomingMoviesViewModel extends ViewModel {
    private PagedList.Config config;
    private UpcomingMoviesDataSourceFactory sourceFactory;
    private LiveData<PagedList<UpcomingMovie>> pagedListLiveData;

    @Inject
    public UpcomingMoviesViewModel(PagedList.Config config) {
        this.config = config;
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
