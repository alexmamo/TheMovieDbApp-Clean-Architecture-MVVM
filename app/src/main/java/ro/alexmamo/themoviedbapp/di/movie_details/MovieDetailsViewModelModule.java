package ro.alexmamo.themoviedbapp.di.movie_details;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ro.alexmamo.themoviedbapp.di.ApiViewModelFactory;
import ro.alexmamo.themoviedbapp.di.ViewModelKey;
import ro.alexmamo.themoviedbapp.movie_details.MovieDetailsViewModel;

@Module
@SuppressWarnings("unused")
public abstract class MovieDetailsViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindMovieDetailsViewModelFactory(ApiViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel.class)
    abstract ViewModel provideMovieDetailsViewModel(MovieDetailsViewModel viewModel);
}