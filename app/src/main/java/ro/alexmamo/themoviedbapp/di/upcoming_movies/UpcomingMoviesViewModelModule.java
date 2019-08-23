package ro.alexmamo.themoviedbapp.di.upcoming_movies;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import ro.alexmamo.themoviedbapp.di.ApiViewModelFactory;
import ro.alexmamo.themoviedbapp.di.ViewModelKey;
import ro.alexmamo.themoviedbapp.upcoming_movies.UpcomingMoviesViewModel;

import static ro.alexmamo.themoviedbapp.utils.Constants.ITEMS_PER_PAGE;

@Module
@SuppressWarnings("unused")
public abstract class UpcomingMoviesViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindApiViewModelFactory(ApiViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(UpcomingMoviesViewModel.class)
    abstract ViewModel provideUpcomingMoviesViewModel(UpcomingMoviesViewModel viewModel);

    @Singleton
    @Provides
    static PagedList.Config providePagedListConfig() {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ITEMS_PER_PAGE)
                .build();
    }
}