package com.devj.dcine.features.video.domain.repository

import com.devj.dcine.features.video.domain.model.Video

interface VideoRepository {
    suspend fun getMovieVideos(id: Int): List<Video>
}