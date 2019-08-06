package ro.alexmamo.themoviedbapp.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ro.alexmamo.themoviedbapp.movie_details.MovieDetailsViewModel;
import ro.alexmamo.themoviedbapp.movie_details.ApiViewModelFactory;

@Module
@SuppressWarnings("unused")
abstract class MovieDetailsViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindMovieDetailsViewModelFactory(ApiViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel.class)
    abstract ViewModel provideMovieDetailsViewModel(MovieDetailsViewModel viewModel);
}