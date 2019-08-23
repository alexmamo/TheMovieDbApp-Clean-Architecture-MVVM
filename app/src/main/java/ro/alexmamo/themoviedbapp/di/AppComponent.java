package ro.alexmamo.themoviedbapp.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import ro.alexmamo.themoviedbapp.BaseApplication;
import ro.alexmamo.themoviedbapp.di.movie_details.MovieDetailsViewModelModule;
import ro.alexmamo.themoviedbapp.di.movie_details.PlayViewModelModule;
import ro.alexmamo.themoviedbapp.di.upcoming_movies.UpcomingMoviesViewModelModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                AppModule.class,
                ActivityBuilderModule.class,
                UpcomingMoviesViewModelModule.class,
                MovieDetailsViewModelModule.class,
                PlayViewModelModule.class
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}