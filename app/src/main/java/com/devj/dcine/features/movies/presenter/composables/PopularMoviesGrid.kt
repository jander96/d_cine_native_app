package com.devj.dcine.features.movies.presenter.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.devj.dcine.core.composables.InfiniteGrid
import com.devj.dcine.features.movies.presenter.PopularMoviesState
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun PopularMoviesGrid(
    modifier: Modifier = Modifier,
    state: PopularMoviesState,
    gridState: LazyGridState = rememberLazyGridState(),
    onLoad: suspend () -> Unit = {},
    onItemClick: (Int) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    InfiniteGrid(
        modifier = modifier,
        gridState = gridState,
        elements = state.popularMovies,
        paginationState = state.paginationState,
        columns = GridCells.Adaptive(200.dp),

        initialLoadingBuilder = {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        },
        errorBuilder = {
            Column (modifier = Modifier.fillMaxSize()) {
                Text(text = state.error?.message ?: "", modifier = Modifier)
                ElevatedButton(onClick = {
                    scope.launch {
                        onLoad()
                    }
                }) {}
            }
        },
        itemBuilder = { movie ->
            Card(modifier = Modifier
                .padding(4.dp)
                .clickable {
                    onItemClick(movie.id)
                }) {
                Column(
                    modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .aspectRatio(1f / 1.5f)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 15.dp, topEnd = 15.dp
                                )
                            ),
                        model = ImageRequest.Builder(LocalContext.current).data(movie.posterPath)
                            .crossfade(true).build(),
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop
                    )
                    Text(text = movie.title, modifier = Modifier.padding(8.dp))
                    Text(
                        text = movie.releaseDate.format(
                            DateTimeFormatter.ofPattern(
                                "d MMM yyyy"
                            )
                        ), modifier = Modifier.padding(8.dp)
                    )
                }
            }
        },
        onLoadMore = onLoad
    )
}