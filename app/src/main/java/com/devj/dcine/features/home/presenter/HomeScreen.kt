package com.devj.dcine.features.home.presenter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import com.devj.dcine.R
import com.devj.dcine.core.presenter.AsyncState
import com.devj.dcine.features.auth.presenter.composables.UserAvatar
import com.devj.dcine.features.movies.presenter.NowPlayingMoviesViewModel
import com.devj.dcine.features.movies.presenter.PopularMoviesViewModel
import com.devj.dcine.features.movies.presenter.TopRatedMoviesViewModel
import com.devj.dcine.features.movies.presenter.UpcomingMoviesViewModel
import com.devj.dcine.features.movies.presenter.composables.MoviesList
import com.devj.dcine.features.settings.presenter.composable.LanguageSelector
import kotlinx.coroutines.async
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    popularMoviesViewModel: PopularMoviesViewModel = koinViewModel(),
    topRatedViewModel: TopRatedMoviesViewModel = koinViewModel(),
    nowPlayingViewModel: NowPlayingMoviesViewModel = koinViewModel(),
    upcomingViewModel: UpcomingMoviesViewModel = koinViewModel(),
    historyViewModel: HistoryViewModel = koinViewModel(),
    onNavigateToMovieDetail: (Int) -> Unit = {}
) {
    val popularMoviesState by popularMoviesViewModel.state.collectAsState()
    val topRatedMoviesState by topRatedViewModel.state.collectAsState()
    val nowPlayingMoviesState by nowPlayingViewModel.state.collectAsState()
    val upcomingMoviesState by upcomingViewModel.state.collectAsState()
    val historyState by historyViewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    key(historyState.size) {
        val carouselState = rememberCarouselState { historyState.size }
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp

        Scaffold(
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.app_title),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    modifier = Modifier,
                    navigationIcon = {
                        UserAvatar(
                            modifier = Modifier.padding(horizontal = 8.dp),
                        )
                    },
                    actions = {
                        LanguageSelector()
                    }

                    )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {

                }
            }
        ) { paddingValues ->
            val safeArea = WindowInsets.safeDrawing
            val scrollState = rememberScrollState()
            val layoutDirection = LocalLayoutDirection.current



            PullToRefreshBox(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues.apply {
                        calculateTopPadding()
                        calculateLeftPadding(layoutDirection)
                        calculateRightPadding(layoutDirection)
                    }),
                isRefreshing = listOf(
                    popularMoviesState,
                    topRatedMoviesState,
                    nowPlayingMoviesState,
                    upcomingMoviesState
                ).any { it.asyncState == AsyncState.LOADING },
                onRefresh = {
                    coroutineScope.async {
                        popularMoviesViewModel.loadMovies(true)
                    }
                    coroutineScope.async {
                        topRatedViewModel.loadMovies(true)
                    }
                    coroutineScope.async {
                        nowPlayingViewModel.loadMovies(true)
                    }
                    coroutineScope.async {
                        upcomingViewModel.loadMovies(true)
                    }
                },
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                ) {
                    val (carousel, popular, topRated, upcoming, nowPlaying) = createRefs()
                    Box(modifier = Modifier .constrainAs(carousel) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                        HorizontalMultiBrowseCarousel(
                            state = carouselState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            preferredItemWidth = screenWidth,
                            itemSpacing = 8.dp,
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                        ) { i ->

                            val movie = historyState[i]
                            AsyncImage(
                                model = movie.backdropPath,
                                   contentDescription = movie.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(280.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable { onNavigateToMovieDetail(movie.id) }
                            )
                        }
                        if (historyState.isNotEmpty()) {
                            Surface(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(start = 16.dp, bottom = 16.dp)
                                    .clip(MaterialTheme.shapes.small)
                            ) {
                                Text(
                                    text = "History",
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }
                    }
                    MoviesList(
                        listTitle = "Popular",
                        state = popularMoviesState,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .constrainAs(popular) {
                                top.linkTo(carousel.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(bottom = 40.dp),
                        onLoad = { popularMoviesViewModel.loadMovies(it) },
                        onItemClick = onNavigateToMovieDetail
                    )

                    MoviesList(
                        listTitle = "Top Rated",
                        state = topRatedMoviesState,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .constrainAs(topRated) {
                                top.linkTo(popular.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(bottom = 40.dp),
                        onLoad = { topRatedViewModel.loadMovies(it) },
                        onItemClick = onNavigateToMovieDetail
                    )

                    MoviesList(
                        listTitle = "Now Playing",
                        state = nowPlayingMoviesState,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .constrainAs(nowPlaying) {
                                top.linkTo(topRated.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(bottom = 40.dp),
                        onLoad = { nowPlayingViewModel.loadMovies(it) },
                        onItemClick = onNavigateToMovieDetail
                    )
                    MoviesList(
                        listTitle = "Upcoming",
                        state = upcomingMoviesState,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .constrainAs(upcoming) {
                                top.linkTo(nowPlaying.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            },
                        onLoad = { upcomingViewModel.loadMovies(it) },
                        onItemClick = onNavigateToMovieDetail
                    )
                }
            }
        }

    }
}