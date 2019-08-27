package ro.alexmamo.themoviedbapp.movie_details.play;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class PlayVideoViewModel extends ViewModel {
    private PlayRepository repository;
    public MutableLiveData<TrailersApiResponse> trailersLiveData;

    @Inject
    PlayVideoViewModel(PlayRepository repository) {
        this.repository = repository;
    }

    public void setMovieId(int movieId) {
        trailersLiveData = repository.addTrailersToLiveData(movieId);
    }
}