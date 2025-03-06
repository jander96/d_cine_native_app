package com.devj.dcine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.devj.dcine.core.composables.InfiniteGrid
import com.devj.dcine.features.movies.presenter.PopularMoviesViewModel
import com.devj.dcine.ui.theme.DCineTheme
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DCineTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android", modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, popularMoviesViewModel: PopularMoviesViewModel = koinViewModel()) {
    val state by popularMoviesViewModel.state.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()


    InfiniteGrid(
        gridState = gridState,
        elements = state.popularMovies,
        paginationState = state.paginationState,
        columns = GridCells.Adaptive(200.dp),
        initialLoadingBuilder = {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        },
        itemBuilder = { movie ->
            Card(modifier = Modifier.padding(4.dp)) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .aspectRatio(1f / 1.5f)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 15.dp,
                                    topEnd = 15.dp
                                )
                            ),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(movie.posterPath)
                            .crossfade(true)
                            .build(),
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
        onLoadMore = {popularMoviesViewModel.loadMovies()}
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DCineTheme {
        Greeting("Android")
    }
}