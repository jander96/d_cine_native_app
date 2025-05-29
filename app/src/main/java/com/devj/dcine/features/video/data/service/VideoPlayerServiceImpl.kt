package com.devj.dcine.features.video.data.service

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.devj.dcine.features.video.domain.service.VideoPlayerService

class VideoPlayerServiceImpl(val player: ExoPlayer) : VideoPlayerService {


    @OptIn(UnstableApi::class)
    override fun prepareMediaSource(uri: String) {

        val mediaSource = ProgressiveMediaSource.Factory(
            DefaultHttpDataSource.Factory()
        ).createMediaSource(MediaItem.fromUri(uri))

        player.addMediaSource(mediaSource)
        player.prepare()
    }


    override fun playVideo(videoId: String) {
        player.play()
    }

    override fun pauseVideo() = player.pause()

    override fun stopVideo() = player.stop()

    override fun seekTo(position: Long) = player.seekTo(position)

    override fun getCurrentPosition() = player.currentPosition

    override fun cleanUp() = player.release()
}