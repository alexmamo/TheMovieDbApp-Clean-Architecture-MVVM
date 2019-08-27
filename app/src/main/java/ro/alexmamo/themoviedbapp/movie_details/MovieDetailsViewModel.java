package ro.alexmamo.themoviedbapp.movie_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class MovieDetailsViewModel extends ViewModel {
    private MovieDetailsRepository repository;
    MutableLiveData<MovieDetails> movieDetailsLiveData;

    @Inject
    MovieDetailsViewModel(MovieDetailsRepository repository) {
        this.repository = repository;
    }

    void setMovieId(int movieId) {
        movieDetailsLiveData = repository.addMovieDetailsToLiveData(movieId);
    }
}