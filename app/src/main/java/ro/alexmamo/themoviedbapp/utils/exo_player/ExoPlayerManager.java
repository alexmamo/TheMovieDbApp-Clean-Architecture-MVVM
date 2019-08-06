package ro.alexmamo.themoviedbapp.utils.exo_player;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class ExoPlayerManager implements Player.EventListener {
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static ExoPlayerManager mInstance = null;
    private PlayerView mPlayerView;
    private DefaultDataSourceFactory dataSourceFactory;
    private String uriString = "";
    private ArrayList<String> mPlayList = null;
    private Integer playlistIndex = 0;
    private CallBacks playerCallBack;
    private SimpleExoPlayer mPlayer;

    private ExoPlayerManager(Context mContext) {
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

        mPlayerView = new PlayerView(mContext);
        mPlayerView.setUseController(true);
        mPlayerView.requestFocus();
        mPlayerView.setPlayer(mPlayer);

        Uri mp4VideoUri = Uri.parse(uriString);

        dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, "androidwave"), BANDWIDTH_METER);

        final MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mp4VideoUri);

        mPlayer.prepare(videoSource);
        mPlayer.addListener(this);
    }

    public static ExoPlayerManager getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new ExoPlayerManager(mContext);
        }
        return mInstance;
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == 4 && mPlayList != null && playlistIndex + 1 < mPlayList.size()) {
            playlistIndex++;
            playerCallBack.onItemClickCallBack(playlistIndex);
            playStream(mPlayList.get(playlistIndex));
        } else if (playbackState == 4 && mPlayList != null && playlistIndex + 1 == mPlayList.size()) {
            mPlayer.setPlayWhenReady(false);
        }

        if (playbackState == 4 && playerCallBack != null) {
            playerCallBack.onPlayingEnd();
        }
    }

    public PlayerView getPlayerView() {
        return mPlayerView;
    }

    public void playStream(String urlToPlay) {
        uriString = urlToPlay;
        Uri mp4VideoUri = Uri.parse(uriString);
        MediaSource videoSource;
        if (uriString.toUpperCase().contains("M3U8")) {
            videoSource = new HlsMediaSource(mp4VideoUri, dataSourceFactory, null, null);
        } else {
            mp4VideoUri = Uri.parse(urlToPlay);
            videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, new DefaultExtractorsFactory(),
                    null, null);
        }
        mPlayer.prepare(videoSource);
        mPlayer.setPlayWhenReady(true);

    }

    public void destroyPlayer() {
        mPlayer.stop();
    }

    public Boolean isPlayerPlaying() {
        return mPlayer.getPlayWhenReady();
    }
}