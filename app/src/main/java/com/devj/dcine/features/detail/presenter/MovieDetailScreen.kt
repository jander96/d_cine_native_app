package com.devj.dcine.features.detail.presenter

import android.content.res.Resources
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devj.dcine.core.composables.CollapsingToolbar
import com.devj.dcine.core.composables.Space16
import com.devj.dcine.core.composables.Space32
import com.devj.dcine.core.composables.management.states.toolbar.TScrollState
import com.devj.dcine.core.composables.management.states.toolbar.ToolbarState
import com.devj.dcine.core.composables.rememberGasmonicBrush
import com.devj.dcine.core.composables.shimmerBrush
import com.devj.dcine.features.detail.domain.Actor
import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.detail.presenter.composables.GenresTags
import com.devj.dcine.features.detail.presenter.composables.MovieActors
import com.devj.dcine.features.detail.presenter.composables.MovieCompanies
import com.devj.dcine.features.detail.presenter.composables.MovieOverview
import com.devj.dcine.features.detail.presenter.composables.MovieStats
import com.devj.dcine.features.detail.presenter.composables.SimilarMoviesGrid
import com.devj.dcine.features.movies.domain.Movie
import com.devj.dcine.features.video.presenter.MovieVideosState
import com.devj.dcine.features.video.presenter.VideoViewModel
import com.devj.dcine.features.video.presenter.VideosGrid
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private val MinToolbarHeight = 96.dp
private val MaxToolbarHeight = 300.dp

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = koinViewModel(),
    similarMoviesViewModel: SimilarMoviesViewModel = koinViewModel(parameters = {parametersOf(movieId)}),
    videoViewModel: VideoViewModel = koinViewModel(parameters = { parametersOf(movieId) }),
    onBackClick: () -> Unit = {},
    onSimilarMovieClick: (movie: Movie) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val videoState by videoViewModel.state.collectAsStateWithLifecycle()
    val similarMoviesState by similarMoviesViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.getMovie(movieId)
    }


    when (state) {
        MovieState.Initial -> Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxSize()
                .background(
                    shimmerBrush()
                )
        ) {
            CircularProgressIndicator()
        }

        MovieState.Loading -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(shimmerBrush())
        ) {
            CircularProgressIndicator()
        }

        is MovieState.Success -> {
            val movie = (state as MovieState.Success).movie
            val actors = (state as MovieState.Success).actors

            MovieDetailView(
                movie = movie,
                videoState = videoState,
                similarMoviesState = similarMoviesState,
                actors = actors,
                onBackClick = onBackClick,
                onLoadMoreSimilarMovies = {
                    similarMoviesViewModel.loadMovies()
                },
                onSimilarMovieClick = onSimilarMovieClick
            )
        }

        is MovieState.Error -> {
            val error = (state as MovieState.Error).error
            Text(error.toString())
        }
    }


}


@Composable
fun MovieDetailView(
    modifier: Modifier = Modifier,
    movie: MovieDetail,
    videoState: MovieVideosState,
    similarMoviesState: SimilarMoviesState,
    actors: List<Actor>,
    onBackClick: () -> Unit = {},
    onLoadMoreSimilarMovies: suspend  () -> Unit = {},
    onSimilarMovieClick: (movie: Movie)->Unit = {}
) {
    val scrollState = rememberScrollState()
    val toolbarHeightRange = with(LocalDensity.current) {
        MinToolbarHeight.roundToPx()..MaxToolbarHeight.roundToPx()
    }
    val toolbarState = rememberToolbarState(toolbarHeightRange)
    toolbarState.scrollValue = scrollState.value

    Surface(
        modifier = modifier
    ) {
        Box {
            MovieDetailBody(
                modifier = Modifier.fillMaxSize(),
                movie = movie,
                videoState = videoState,
                similarMoviesState = similarMoviesState,
                actors = actors,
                scrollState = scrollState,
                paddingValues = PaddingValues(top = with(LocalDensity.current) { toolbarState.height.toDp() }),
                onLoadMoreSimilarMovies = onLoadMoreSimilarMovies,
                onSimilarMovieClick = onSimilarMovieClick
            )
            CollapsingToolbar(
                onBackClick = onBackClick,
                title = { Text(movie.title) },
                toolbarState = toolbarState,
                backgroundImage = movie.backdropPath,
                modifier = Modifier.fillMaxWidth()

            )

        }
    }
}

@Composable
private fun rememberToolbarState(toolbarHeightRange: IntRange): ToolbarState {
    return rememberSaveable(saver = TScrollState.Saver) {
        TScrollState(toolbarHeightRange)
    }
}

@Composable
fun MovieDetailBody(
    movie: MovieDetail,
    videoState: MovieVideosState,
    similarMoviesState: SimilarMoviesState,
    actors: List<Actor>,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    paddingValues: PaddingValues = PaddingValues(0.dp),
    widowIfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    onLoadMoreSimilarMovies: suspend ()-> Unit,
    onSimilarMovieClick: (movie: Movie)-> Unit
) {

    Column(
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {

        Space16()

        MovieStats(modifier = Modifier, movie = movie)

        Space16()

        GenresTags(modifier = Modifier, genres = movie.genres)

        Space16()
        MovieCompanies(modifier = Modifier, movie = movie)

        Space16()

        MovieOverview(
            modifier = Modifier, movie = movie
        )
        Space16()

        MovieActors(modifier = Modifier, actors = actors)
        Space16()
        VideosGrid(modifier = Modifier, videosState = videoState)
        Space16()
        SimilarMoviesGrid(
            modifier = Modifier,
            similarMoviesState = similarMoviesState,
            onLoadMore = onLoadMoreSimilarMovies,
            onItemClick = onSimilarMovieClick,
            )
        Spacer(modifier = Modifier.height(120.dp))

    }
}
