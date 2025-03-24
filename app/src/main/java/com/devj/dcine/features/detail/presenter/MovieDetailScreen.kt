package com.devj.dcine.features.detail.presenter

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devj.dcine.core.composables.CollapsingToolbar
import com.devj.dcine.core.composables.management.states.toolbar.TScrollState
import com.devj.dcine.core.composables.management.states.toolbar.ToolbarState
import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.detail.mockMovieDetail
import com.devj.dcine.features.detail.presenter.composables.MovieStats
import com.devj.dcine.features.detail.presenter.composables.MovieTitle
import com.devj.dcine.features.detail.presenter.composables.PlayButton
import org.koin.androidx.compose.koinViewModel

private val MinToolbarHeight = 96.dp
private val MaxToolbarHeight = 300.dp

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.getMovie(movieId)
    }


    when (state) {
        MovieState.Initial -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }

        MovieState.Loading -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }

        is MovieState.Success -> {
            val movie = (state as MovieState.Success).movie

            MovieDetailView(movie = movie, onBackClick = onBackClick)
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
    onBackClick: () -> Unit = {},
    ) {
    val scrollState = rememberScrollState()
    val toolbarHeightRange = with(LocalDensity.current) {
        MinToolbarHeight.roundToPx()..MaxToolbarHeight.roundToPx()
    }
    val toolbarState = rememberToolbarState(toolbarHeightRange)
    toolbarState.scrollValue = scrollState.value

    Box(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surface)
    ) {
        MovieDetailBody(
            modifier = Modifier.fillMaxSize(),
            movie = movie,
            scrollState = scrollState,
            paddingValues = PaddingValues(top =with(LocalDensity.current) { toolbarState.height.toDp()})
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

@Composable
private fun rememberToolbarState(toolbarHeightRange: IntRange): ToolbarState {
    return  rememberSaveable(saver = TScrollState.Saver) {
        TScrollState(toolbarHeightRange)
    }
}

@Composable
fun MovieDetailBody(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    paddingValues: PaddingValues = PaddingValues(0.dp),
    movie: MovieDetail
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)
    ) {
//        val (title, stats, playVideoButton, overview, actors) = createRefs()

//        MovieTitle(
//            modifier = Modifier
//                .constrainAs(title) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                },
//            movie = movie
//        )
//
//        MovieStats(modifier = Modifier.constrainAs(stats) {
//            top.linkTo(title.bottom)
//            start.linkTo(parent.start)
//            end.linkTo(parent.end)
//        }, movie = movie)
//
//        PlayButton(
//            modifier = Modifier
//                .constrainAs(playVideoButton) {
//                    top.linkTo(stats.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//
//                })

        Text(movie.overview.repeat(12),)

    }
}

@Preview
@Composable
private fun MovieDetailViewPreview() {


    MovieDetailView(movie = mockMovieDetail)

}
