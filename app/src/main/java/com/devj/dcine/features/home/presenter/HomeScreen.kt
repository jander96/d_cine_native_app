package com.devj.dcine.features.home.presenter

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.devj.dcine.features.movies.presenter.PopularMoviesViewModel
import com.devj.dcine.features.movies.presenter.composables.PopularMoviesGrid
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    popularMoviesViewModel: PopularMoviesViewModel = koinViewModel(),
    onNavigateToMovieDetail: (Int) -> Unit = {}
) {
    val popularMoviesState by popularMoviesViewModel.state.collectAsState()
    Scaffold { paddingValues ->

        PopularMoviesGrid(
            state = popularMoviesState,
            modifier = Modifier.padding(paddingValues),
            onLoad = { popularMoviesViewModel.loadMovies() },
            onItemClick = onNavigateToMovieDetail
        )
    }

}