package com.devj.dcine.features.video.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devj.dcine.core.presenter.AsyncState
import com.devj.dcine.features.video.domain.model.Video
import com.devj.dcine.features.video.domain.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VideoViewModel(private val movieId: Int, private val videoRepository: VideoRepository) :
    ViewModel() {

    private val _viewState: MutableStateFlow<MovieVideosState> =
        MutableStateFlow(MovieVideosState())
    val state: StateFlow<MovieVideosState> get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            getVideos()
        }
    }

    suspend fun getVideos() {

        try {
            _viewState.update { it.copy(asyncState = AsyncState.LOADING) }
            val videos = videoRepository.getMovieVideos(movieId)
            _viewState.update {
                it.copy(
                    asyncState = AsyncState.SUCCESS,
                    videos = videos
                )
            }
        } catch (e: Exception) {
            _viewState.update {
                it.copy(
                    asyncState = AsyncState.ERROR,
                    error = e
                )
            }
        }

    }

}

data class MovieVideosState(
    val asyncState: AsyncState = AsyncState.INITIAL,
    val videos: List<Video> = emptyList(),
    val error: Throwable? = null,
)

