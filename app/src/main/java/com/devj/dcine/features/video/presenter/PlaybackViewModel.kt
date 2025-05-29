package com.devj.dcine.features.video.presenter


import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import com.devj.dcine.features.video.domain.model.Video
import com.devj.dcine.features.video.domain.service.VideoPlayerService

class PlaybackViewModel(
    private val video: Video,
    private val playerService: VideoPlayerService,
    val player: ExoPlayer
) : ViewModel() {

    init {
        playerService.prepareMediaSource(video.getUrl())
    }

    fun playVideo() {
        playerService.playVideo(video.id)
    }

    fun pauseVideo() {
        playerService.pauseVideo()
    }

    fun stopVideo() {
        playerService.stopVideo()
    }

    fun seekTo(position: Long) {
        playerService.seekTo(position)
    }

    fun getCurrentPosition(): Long {
        return playerService.getCurrentPosition()
    }


    override fun onCleared() {
        playerService.cleanUp()
        super.onCleared()
    }

}