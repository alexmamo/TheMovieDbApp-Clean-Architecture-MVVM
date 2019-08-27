package ro.alexmamo.themoviedbapp.movie_details;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.RequestManager;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

import javax.inject.Inject;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import dagger.android.support.DaggerAppCompatActivity;
import ro.alexmamo.themoviedbapp.R;
import ro.alexmamo.themoviedbapp.databinding.ActivityMovieDetailsBinding;
import ro.alexmamo.themoviedbapp.movie_details.play.PlayVideoViewModel;
import ro.alexmamo.themoviedbapp.movie_details.play.Trailer;
import ro.alexmamo.themoviedbapp.utils.exo_player.ExoPlayerManager;

import static ro.alexmamo.themoviedbapp.utils.HelperClass.getEntirePosterPathUrl;

public class MovieDetailsActivity extends DaggerAppCompatActivity {
    @Inject MovieDetailsViewModel movieDetailsViewModel;
    @Inject PlayVideoViewModel playVideoViewModel;
    @Inject RequestManager requestManager;
    private int movieId;
    private ActivityMovieDetailsBinding activityMovieDetailsBinding;
    private Dialog trailerDialog;
    private ExoPlayerManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        movieId = getMovieIdFromIntent();
        setActionBar();
        setMovieIdInMovieDetailsViewModel();
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

    private void setMovieIdInMovieDetailsViewModel() {
        movieDetailsViewModel.setMovieId(movieId);
        movieDetailsViewModel.movieDetailsLiveData.observe(this, movieDetails -> {
            setPosterPathImageView(movieDetails.posterPath);
            activityMovieDetailsBinding.setMovieDetails(movieDetails);
        });
    }

    private void setPosterPathImageView(String posterPath) {
        if (posterPath != null) {
            String entirePosterPath = getEntirePosterPathUrl(posterPath);
            requestManager.load(entirePosterPath).into(activityMovieDetailsBinding.posterPathImageView);
        }
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
        playVideoViewModel.trailersLiveData.observe(this, trailersApiResponse -> {
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