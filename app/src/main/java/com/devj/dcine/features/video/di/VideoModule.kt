package com.devj.dcine.features.video.di

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.exoplayer.ExoPlayer
import com.devj.dcine.features.video.data.repository.VideoRepositoryImpl
import com.devj.dcine.features.video.data.service.VideoPlayerServiceImpl
import com.devj.dcine.features.video.domain.model.Video
import com.devj.dcine.features.video.domain.repository.VideoRepository
import com.devj.dcine.features.video.domain.service.VideoPlayerService
import com.devj.dcine.features.video.presenter.PlaybackViewModel
import com.devj.dcine.features.video.presenter.VideoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.core.module.dsl.*


val videoModule = module {
    factory<VideoRepository> {  VideoRepositoryImpl(get()) }
    viewModel {  (movieId: Int) ->
        VideoViewModel(
            movieId = movieId,
            videoRepository = get()
        )
    }

    single {
        ExoPlayer.Builder(androidContext()).build().apply {
            setHandleAudioBecomingNoisy(true)
            playWhenReady = true
        }
    }

    single {
        ExoPlayer.Builder(androidContext()).build()
    }

    single<VideoPlayerService> {
        VideoPlayerServiceImpl(
            player = get()
        )
    }


    viewModel {  (video: Video) ->
        PlaybackViewModel(
            video = video,
            playerService = get(),
            player = get()
        )
    }
}