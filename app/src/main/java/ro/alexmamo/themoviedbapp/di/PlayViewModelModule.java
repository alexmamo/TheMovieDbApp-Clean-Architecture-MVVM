package ro.alexmamo.themoviedbapp.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ro.alexmamo.themoviedbapp.movie_details.ApiViewModelFactory;
import ro.alexmamo.themoviedbapp.movie_details.play.PlayVideoViewModel;

@Module
@SuppressWarnings("unused")
abstract class PlayViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindMovieDetailsViewModelFactory(ApiViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(PlayVideoViewModel.class)
    abstract ViewModel provideMovieDetailsViewModel(PlayVideoViewModel viewModel);
}