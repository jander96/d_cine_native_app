package com.devj.dcine.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.devj.dcine.features.detail.presenter.MovieDetailScreen
import com.devj.dcine.features.home.presenter.HomeScreen

fun NavGraphBuilder.mainGraph(
    navController: NavController
) {

    composable<Screen.Home> {
        HomeScreen(
            onNavigateToMovieDetail = {
                navController.navigate(Screen.MovieDetail(it))
            }
        )
    }

    composable<Screen.MovieDetail> {
        val detail = it.toRoute<Screen.MovieDetail>()
        MovieDetailScreen(
            movieId = detail.id,
            onBackClick = {navController.popBackStack()}
        )
    }


}