package ro.alexmamo.themoviedbapp.movie_details;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieDetailsViewModelTest {
    private MutableLiveData<MovieDetails> mockLiveData = new MutableLiveData<>();
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Mock
    private MovieDetailsViewModel viewModel;

    @Before
    public void setUp() {
        MovieDetails fakeMovieDetails = new MovieDetails();
        fakeMovieDetails.id = 0;
        fakeMovieDetails.title = "FakeTitle";
        fakeMovieDetails.language = "FakeLanguage";
        fakeMovieDetails.genres = new ArrayList();
        fakeMovieDetails.overview = "FakeOverview";
        fakeMovieDetails.releaseDate = "FakeReleaseDate";
        fakeMovieDetails.posterPath = "FakePosterPath";
        mockLiveData.setValue(fakeMovieDetails);
    }

    @Test
    public void movieDetailsLiveDataValidatorIfMockData() {
        when(viewModel.getMovieDetailsLiveData()).thenReturn(mockLiveData);
        MovieDetails fakeMovieDetails = mockLiveData.getValue();
        if (fakeMovieDetails != null) {
            System.out.println(mockLiveData.getValue().title);
        }
    }
}