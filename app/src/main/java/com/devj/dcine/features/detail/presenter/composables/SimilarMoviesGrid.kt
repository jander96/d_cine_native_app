package com.devj.dcine.features.detail.presenter.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.devj.dcine.R
import com.devj.dcine.core.composables.InfiniteHorizontalGrid
import com.devj.dcine.core.composables.shimmerBrush
import com.devj.dcine.features.detail.presenter.SimilarMoviesState
import com.devj.dcine.features.movies.domain.Movie

@Composable
fun SimilarMoviesGrid(
    modifier: Modifier = Modifier,
    similarMoviesState: SimilarMoviesState,
    onLoadMore :suspend () -> Unit,
    onItemClick: (movie: Movie)-> Unit
    ) {

    Box(modifier = modifier.height(390.dp)) {
        Column {
            Text(
                text = "Similar Movies",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            InfiniteHorizontalGrid(
                elements = similarMoviesState.movies,
                modifier = modifier,
                rows = GridCells.Adaptive(minSize = 130.dp),
                itemBuilder = {
                    MovieItem(it, onClick = onItemClick)
                },
                gridState = rememberLazyGridState(),
                onLoadMore = onLoadMore,
                paginationState = similarMoviesState.paginationState,
                initialLoadingBuilder = {
                    CircularProgressIndicator()
                },
                errorBuilder = {
                    Text(similarMoviesState.error.toString())
                },
                loadingNextBuilder = {
                    CircularProgressIndicator()
                },
                emptyBuilder = {
                    Text("There is not similar movies")
                },
                endOfListBuilder = {
                    Text("No more similar movies")
                },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
        }

    }



}

@Composable
fun MovieItem(movie: Movie, modifier : Modifier = Modifier, onClick: (movie: Movie)-> Unit) {
    val painter = rememberAsyncImagePainter(
        movie.backdropPath,
        contentScale = ContentScale.Crop,
    )
    val state = painter.state.collectAsState()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        when (state.value) {
            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .height(180.dp)
                        .aspectRatio(16f/9f)
                        .background(shimmerBrush()),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(strokeWidth = 1.dp, modifier = Modifier)
                }
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    modifier = Modifier
                        .clickable {
                            onClick(movie)
                        }
                        .clip(MaterialTheme.shapes.medium)
                        .weight(4f),


                    painter = painter,
                    contentScale = ContentScale.Crop,
                    contentDescription = movie.title
                )
            }

            is AsyncImagePainter.State.Error -> {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxWidth()
                        .aspectRatio(13f/9f)
                        .clip(MaterialTheme.shapes.medium),
                    painter = painterResource(R.drawable.no_photo),
                    contentDescription = movie.title
                )
            }
        }
        Text(movie.title.run {
            if(isEmpty()) return@run "No title"
            val name = if(length > 38) "${take(38)}..." else this
            name;
        }, modifier = Modifier.weight(1f),style = MaterialTheme.typography.bodySmall)
    }
}

