package com.devj.dcine.features.search.presenter

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.devj.dcine.R
import com.devj.dcine.core.composables.AsyncImageShimmer
import com.devj.dcine.core.composables.ExpandableCard
import com.devj.dcine.core.composables.InfiniteVerticalGrid
import com.devj.dcine.core.composables.MenuOptionItem
import com.devj.dcine.core.composables.MenuOptions
import com.devj.dcine.core.composables.Space40
import com.devj.dcine.core.composables.Space8
import com.devj.dcine.core.composables.shimmerBrush
import com.devj.dcine.core.utils.extensions.toStringWithDecimal
import com.devj.dcine.features.filters.domain.models.SortBy
import com.devj.dcine.features.filters.presenter.MovieFilterViewModel
import com.devj.dcine.features.filters.presenter.composables.YearPickerDialog
import com.devj.dcine.features.search.presenter.composables.GenresFilter
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = koinViewModel(),
    filtersViewModel: MovieFilterViewModel = koinViewModel(),
    genresViewModel: GenresViewModel = koinViewModel(),
    companiesViewModel: CompaniesViewModel = koinViewModel(),
    onItemClick: (Int) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val searchState by searchViewModel.state.collectAsState()
    val genresState by genresViewModel.state.collectAsState()
    val companiesState by companiesViewModel.state.collectAsState()
    val filterState by filtersViewModel.state.collectAsState()
    val orderState by filtersViewModel.sortBy.collectAsState()
    val gridState = rememberLazyGridState()
    var showDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        searchViewModel.events.collect { event ->
            when (event) {
                is SearchViewModel.SearchUiEvent.Search -> {

                }

                is SearchViewModel.SearchUiEvent.FilterChanged -> {
                    searchViewModel.loadMovies(true)
                }

                is SearchViewModel.SearchUiEvent.OrderChanged -> {
                    searchViewModel.loadMovies(true)
                }
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Search")
            }, actions = {
                MenuOptions(
                    selected = orderState,
                    modifier = Modifier,
                    icon =  R.drawable.sort,
                    onClick = {
                       filtersViewModel.setOrder(it)
                    },
                    options = listOf(
                        MenuOptionItem(
                            value = SortBy.POPULARITY_ASC,
                            title = "Popularity Asc",
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Popularity Asc"
                                )
                            }
                        ),
                        MenuOptionItem(
                            value = SortBy.POPULARITY_DESC,
                            title = "Popularity Desc",
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Popularity Asc"
                                )
                            }
                        ),
                        MenuOptionItem(
                            value = SortBy.RELEASE_DATE_ASC,
                            title = "Release Date Asc",
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Popularity Asc"
                                )
                            }
                        ),
                        MenuOptionItem(
                            value = SortBy.RELEASE_DATE_DESC,
                            title = "Release date Desc",
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Popularity Asc"
                                )
                            }
                        ),
                        MenuOptionItem(
                            value = SortBy.VOTE_AVERAGE_ASC,
                            title = "Vote average Asc",
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Popularity Asc"
                                )
                            }
                        ),
                        MenuOptionItem(
                            value = SortBy.VOTE_AVERAGE_DESC,
                            title = "Vote average Desc",
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Popularity Asc"
                                )
                            }
                        ),
                        MenuOptionItem(
                            value = SortBy.TITLE_ASC,
                            title = "Title Asc",
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Title Asc"
                                )
                            }
                        ),
                        MenuOptionItem(
                            value = SortBy.TITLE_DESC,
                            title = "Title Desc",
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Title Asc"
                                )
                            }
                        ),

                    )
                    )
                IconButton(onClick = {
                    showBottomSheet = true
                }) {
                    Icon(
                        painter = painterResource(R.drawable.filters),
                        contentDescription = "Filter",
                        modifier = Modifier.size(24.dp)
                    )
                }
            })
        }
    ) { contentPadding ->

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Text(
                    "Filters",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                Surface(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            showDialog = true
                        }


                ) {
                    Text(
                        "Select year: ${filterState.year ?: LocalDate.now().year}",
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Space8()

                if (showDialog) {
                    YearPickerDialog(
                        initialYear = filterState.year ?: LocalDate.now().year,
                        onYearSelected = {
                            filtersViewModel.setMovieFilters(fn = { filters ->
                                filters.copy(year = it)
                            })
                        },
                        onDismiss = { showDialog = false }
                    )
                }
                ExpandableCard(
                    title = "Select Genres"
                ) {
                    GenresFilter(
                        genres = genresState.genres,
                        selectedGenres = filterState.genres,
                        onGenreSelected = { genres ->
                            filtersViewModel.setMovieFilters(fn = { filters ->
                                filters.copy(genres = genres)
                            })
                        },
                    )
                }
                Space40()
            }
        }


        Column(modifier = Modifier.padding(contentPadding)) {


            Column {


            }
            InfiniteVerticalGrid(

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
                                text = movie.releaseDate?.format(
                                    DateTimeFormatter.ofPattern(
                                        "yyyy"
                                    )
                                ) ?: "No date",
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