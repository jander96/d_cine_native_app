package com.devj.dcine.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object Auth: Screen
    @Serializable
    data object Splash: Screen
    @Serializable
    data object Home : Screen
    @Serializable
    data class MovieDetail(val id: Int): Screen
    @Serializable
    data object Search : Screen
    @Serializable
    data object Wishlist : Screen
    @Serializable
    data object Profile : Screen

}


