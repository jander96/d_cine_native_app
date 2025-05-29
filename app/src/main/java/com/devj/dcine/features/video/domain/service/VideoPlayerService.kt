package com.devj.dcine.features.video.domain.service



interface VideoPlayerService {
    fun prepareMediaSource(uri: String)
    fun playVideo(videoId: String)
    fun pauseVideo()
    fun stopVideo()
    fun seekTo(position: Long)
    fun getCurrentPosition(): Long
    fun cleanUp()
}