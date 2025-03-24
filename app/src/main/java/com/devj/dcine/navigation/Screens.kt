package com.devj.dcine.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object Home : Screen
    @Serializable
    data class MovieDetail(val id: Int): Screen

}


