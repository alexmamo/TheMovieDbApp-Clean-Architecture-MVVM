package ro.alexmamo.themoviedbapp.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ro.alexmamo.themoviedbapp.movie_details.MovieDetailsActivity;
import ro.alexmamo.themoviedbapp.upcoming_movies.UpcomingMoviesActivity;

@Module
@SuppressWarnings("unused")
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract UpcomingMoviesActivity contributeUpcomingMoviesActivity();

    @ContributesAndroidInjector
    abstract MovieDetailsActivity contributeMovieDetailsActivity();
}