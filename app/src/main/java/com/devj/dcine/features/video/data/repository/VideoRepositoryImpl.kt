package com.devj.dcine.features.video.data.repository

import com.devj.dcine.core.data.api.MovieApi
import com.devj.dcine.features.video.domain.model.Video
import com.devj.dcine.features.video.domain.repository.VideoRepository

class VideoRepositoryImpl(private val api: MovieApi) : VideoRepository {
    override suspend fun getMovieVideos(id: Int): List<Video> {
        val response = api.getMovieVideos(id)
        return response.results.map { it.toDomain() }
    }
}