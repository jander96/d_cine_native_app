package com.devj.dcine.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.devj.dcine.features.auth.presenter.AuthPage
import com.devj.dcine.features.auth.presenter.AuthenticationViewModel
import com.devj.dcine.features.detail.presenter.MovieDetailScreen
import com.devj.dcine.features.home.presenter.HomeScreen
import com.devj.dcine.features.profile.ProfileScreen
import com.devj.dcine.features.search.presenter.SearchScreen
import com.devj.dcine.features.splash.presenter.SplashScreen
import com.devj.dcine.features.wishlist.WishlistScreen

fun NavGraphBuilder.mainGraph(
    navController: Navigator,
) {

    composable<Screen.Splash> {
        SplashScreen {
            navController.navigate(Screen.Home)
        }
    }

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
            onBackClick = {navController.popBackStack()},
            onSimilarMovieClick = {
                navController.navigate(Screen.MovieDetail(it.id))
            }
        )
    }

    composable<Screen.Search> {
        SearchScreen(onItemClick = {
            navController.navigate(Screen.MovieDetail(it))
        })
    }
    composable<Screen.Wishlist> {
        WishlistScreen()
    }
    composable<Screen.Profile> {
        ProfileScreen()
    }


}
fun NavGraphBuilder.authGraph(
    navController: NavController,
    authViewModel: AuthenticationViewModel,
) {
    composable<Screen.Auth> {
        AuthPage(
            viewModel = authViewModel,
            onSuccess = {
                navController.navigate(Screen.Home) {
                    popUpTo(Screen.Auth) { inclusive = true }
                }
            }
        )
    }
}