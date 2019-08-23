package ro.alexmamo.themoviedbapp.movie_details;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.bumptech.glide.RequestManager;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import dagger.android.support.DaggerAppCompatActivity;
import ro.alexmamo.themoviedbapp.R;
import ro.alexmamo.themoviedbapp.movie_details.play.PlayVideoViewModel;
import ro.alexmamo.themoviedbapp.movie_details.play.Trailer;
import ro.alexmamo.themoviedbapp.utils.exo_player.ExoPlayerManager;

import static ro.alexmamo.themoviedbapp.utils.HelperClass.getEntirePosterPathUrl;

public class MovieDetailsActivity extends DaggerAppCompatActivity {
    @Inject MovieDetailsViewModel movieDetailsViewModel;
    @Inject PlayVideoViewModel playVideoViewModel;
    @Inject RequestManager requestManager;
    private int movieId;
    private ImageView posterPathImageView;
    private TextView titleAndReleaseDateTextView;
    private TextView languageTextView;
    private TextView genresTextView;
    private TextView overviewTextView;
    private Dialog trailerDialog;
    private ExoPlayerManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieId = getMovieIdFromIntent();
        setActionBar();
        initViews();
        initMovieDetailsViewModel();
    }

    private int getMovieIdFromIntent() {
        return  (int) getIntent().getSerializableExtra("movieId");
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Movie Details");
    }

    private void initViews() {
        posterPathImageView = findViewById(R.id.poster_path_image_view);
        titleAndReleaseDateTextView = findViewById(R.id.title_and_release_date_text_view);
        languageTextView = findViewById(R.id.language_text_view);
        genresTextView = findViewById(R.id.genres_text_view);
        overviewTextView = findViewById(R.id.overview_text_view);
    }

    private void initMovieDetailsViewModel() {
        movieDetailsViewModel.setMovieId(movieId);
        movieDetailsViewModel.getMovieDetailsLiveData().observe(this, movieDetails -> {
            setPosterPathImageView(movieDetails.posterPath);
            setTitleAndReleaseDateTextView(movieDetails.title, movieDetails.releaseDate);
            setLanguageTextView(movieDetails.language);
            setGenresTextView(movieDetails.genres);
            setOverviewTextView(movieDetails.overview);
        });
    }

    private void setPosterPathImageView(String posterPath) {
        if (posterPath != null) {
            String entirePosterPath = getEntirePosterPathUrl(posterPath);
            requestManager.load(entirePosterPath).into(posterPathImageView);
        }
    }

    private void setTitleAndReleaseDateTextView(String title, String releaseDate) {
        String entireTitle = title + " (" + releaseDate.substring(0, 4) + ")";
        titleAndReleaseDateTextView.setText(entireTitle);
    }

    private void setLanguageTextView(String language){
        String entireLanguage = "Language: " + language.substring(0,1).toUpperCase() + language.substring(1);
        languageTextView.setText(entireLanguage);
    }

    private void setOverviewTextView(String overview) {
        overviewTextView.setText(overview);
    }

    private void setGenresTextView(ArrayList genres) {
        StringBuilder genresStringBuilder = new StringBuilder();
        String separator = ", ";
        for (int i = 0 ; i < genres.size(); i++) {
            LinkedTreeMap treeMap = (LinkedTreeMap) genres.get(i);
            String name = (String) treeMap.get("name");
            if (name != null) {
                if (i == genres.size() - 1) {
                    separator = ".";
                }
                genresStringBuilder.append(name).append(separator);
            }
        }
        String allGenres = "Genres: " + genresStringBuilder;
        genresTextView.setText(allGenres);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.play:
                initDialog();
                playFirstTrailer(movieId);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void playFirstTrailer(int movieId) {
        playVideoViewModel.setMovieId(movieId);
        playVideoViewModel.getMovieDetailsLiveData().observe(this, trailersApiResponse -> {
            if (trailersApiResponse != null) {
                List<Trailer> trailers = trailersApiResponse.trailers;
                Trailer firstTrailer = trailers.get(0);
                String youtubeLink = firstTrailer.key;
                extractYoutubeUrl(youtubeLink);
            }
        });
    }

    private void initDialog() {
        trailerDialog = new Dialog(this);
        trailerDialog.setContentView(R.layout.dialog);
        trailerDialog.setOnCancelListener(dialog -> {
            if (manager.isPlayerPlaying()) {
                manager.destroyPlayer();
            }
        });
        trailerDialog.show();

    }

    private void extractYoutubeUrl(String youtubeLink) {
        @SuppressLint("StaticFieldLeak")
        YouTubeExtractor extractor = new YouTubeExtractor(this) {
            @Override
            protected void onExtractionComplete(SparseArray<YtFile> sparseArray, VideoMeta videoMeta) {
                if (sparseArray != null) {
                    playYoutubeVideo(sparseArray.get(18).getUrl(), trailerDialog);
                }
            }
        };
        extractor.extract(youtubeLink, true, true);
    }

    private void playYoutubeVideo(String downloadUrl, Dialog dialog) {
        PlayerView playerView = dialog.findViewById(R.id.player_view);
        manager = ExoPlayerManager.getInstance(this);
        playerView.setPlayer(manager.getPlayerView().getPlayer());
        manager.playStream(downloadUrl);
    }
}