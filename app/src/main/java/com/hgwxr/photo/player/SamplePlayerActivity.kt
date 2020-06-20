package com.hgwxr.photo.player

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.hgwxr.photo.R
import kotlinx.android.synthetic.main.activity_player.*

//const val player_url = "https://media.w3.org/2010/05/sintel/trailer.mp4"
//const val player_url = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"

class SamplePlayerActivity : AppCompatActivity() {

    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0
    private var playWhenReady: Boolean = false

    companion object {
        fun start(activity: Activity) {
            Toast.makeText(activity, "ceshi", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, SamplePlayerActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
    }


    private fun buildMediaSource(uri: Uri): MediaSource? {
        val s =
            "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Mobile Safari/537.36"
        val defaultDataSourceFactory = DefaultDataSourceFactory(
            this,
            Util.getUserAgent(
                this,
                s//"exoplayer-codelab"
            )
        )
        val factory = ProgressiveMediaSource.Factory(defaultDataSourceFactory)
//        val factory = HlsMediaSource.Factory(defaultDataSourceFactory)
//        val factory = SsMediaSource.Factory(defaultDataSourceFactory)
//        val factory = DashMediaSource.Factory(defaultDataSourceFactory)
        val map3 = factory.createMediaSource(Uri.parse(getString(R.string.media_url_mp3)))
        val map4 = factory.createMediaSource(Uri.parse(getString(R.string.media_url_mp4)))
//        val mapDash = factory.createMediaSource(Uri.parse(getString(R.string.media_url_dash)))
//        val map41 = factory.createMediaSource(Uri.parse(getString(R.string.media_url_mp41)))
        return ConcatenatingMediaSource(map3, map4)
//        return map41
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        val player = simplePlayerView.player
        apply {
            val p = (player as SimpleExoPlayer)
            playWhenReady = p.getPlayWhenReady()
            playbackPosition = p.getCurrentPosition()
            currentWindow = p.getCurrentWindowIndex()
            p.release()
            simplePlayerView.player = null
        }


    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT < 24 || simplePlayerView.player == null) {
            initializePlayer()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        simplePlayerView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        )
    }

    private fun initializePlayer() {
        var player = simplePlayerView.player
        if (player == null) {
//            player = SimpleExoPlayer.Builder(this).build()
            val defaultTrackSelector = DefaultTrackSelector(this)
            defaultTrackSelector.setParameters(defaultTrackSelector.buildUponParameters().setMaxVideoSizeSd())
//            player = ExoPlayerFactory.newSimpleInstance(this, defaultTrackSelector);
            player = SimpleExoPlayer.Builder(this).setTrackSelector(defaultTrackSelector).build()
        }
        player.let {
            it.playWhenReady = this.playWhenReady
            it.seekTo(this.playbackPosition)
            val buildMediaSource =
                this.buildMediaSource(Uri.parse(getString(R.string.media_url_mp4)))
            if (buildMediaSource != null) {
                (it as SimpleExoPlayer).prepare(buildMediaSource, false, false)
            };

        }
        simplePlayerView.player = player

    }

    override fun onDestroy() {
        super.onDestroy()
        simplePlayerView.player?.release()
    }
}