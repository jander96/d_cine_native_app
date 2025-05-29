package com.devj.dcine.features.search.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.devj.dcine.core.composables.AsyncImageShimmer
import com.devj.dcine.core.composables.InfiniteGrid
import com.devj.dcine.core.composables.shimmerBrush
import com.devj.dcine.core.utils.extensions.toStringWithDecimal
import com.devj.dcine.features.filters.presenter.MovieFilterViewModel
import com.devj.dcine.features.filters.presenter.composables.YearPickerDialog
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = koinViewModel(),
    filtersViewModel : MovieFilterViewModel = koinViewModel(),
    onItemClick: (Int) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val searchState by searchViewModel.state.collectAsState()
    val gridState = rememberLazyGridState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedYear by remember { mutableStateOf(LocalDate.now().year) }

    LaunchedEffect(Unit) {
        searchViewModel.events.collect { event ->
            when (event) {
                is SearchViewModel.SearchUiEvent.Search -> {

                }
                is SearchViewModel.SearchUiEvent.FilterChanged  -> {
                    searchViewModel.loadMovies(true)
                }
            }
        }
    }


    Scaffold { contentPadding ->


        Column(modifier = Modifier.padding(contentPadding)) {



            Column {
                Button(onClick = { showDialog = true }) {
                    Text("Seleccionar año: $selectedYear")
                }

                if (showDialog) {
                    YearPickerDialog(
                        initialYear = selectedYear,
                        onYearSelected = {
                            selectedYear = it
                            filtersViewModel.setMovieFilters(fn ={ filters ->
                                filters.copy(year = it)
                            })
                                         },
                        onDismiss = { showDialog = false }
                    )
                }
            }
            InfiniteGrid(

                columns = GridCells.Adaptive(minSize = 128.dp),
                elements = searchState.movies,
                paginationState = searchState.paginationState,
                modifier = Modifier,
                gridState = gridState,
                initialLoadingBuilder = {
                    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                    LazyRow(modifier = Modifier.width(screenWidth)) {
                        items(10) {
                            Box(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .height(180.dp)
                                    .aspectRatio(1f / 1.5f)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(shimmerBrush())
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center),
                                    strokeWidth = 1.dp
                                )
                            }
                        }
                    }
                },
                errorBuilder = {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = searchState.error?.message ?: "", modifier = Modifier)
                        ElevatedButton(onClick = {
                            scope.launch {
                                searchViewModel.loadMovies(false)
                            }
                        }) {}
                    }
                },
                itemBuilder = { movie ->

                    Column(
                        modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImageShimmer(modifier = Modifier.clickable {
                            onItemClick(movie.id)
                        }, model = movie.posterPath, contentDescription = movie.title)

                        Text(
                            text = movie.title.run {
                                val maxChars = 16
                                val result = if (length > maxChars) {
                                    take(maxChars) + "..."
                                } else {
                                    this
                                }
                                result
                            },
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Row {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = movie.voteAverage.toStringWithDecimal(1),
                                style = MaterialTheme.typography.labelSmall,
                            )
                            VerticalDivider(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(12.dp)
                            )
                            Text(
                                text = movie.releaseDate.format(
                                    DateTimeFormatter.ofPattern(
                                        "yyyy"
                                    )
                                ),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                    }
                },
                onLoadMore = { searchViewModel.loadMovies(false) }
            )
        }
    }
}