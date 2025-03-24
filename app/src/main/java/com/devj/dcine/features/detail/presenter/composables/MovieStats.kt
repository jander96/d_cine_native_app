package com.devj.dcine.features.detail.presenter.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devj.dcine.features.detail.domain.MovieDetail


@Composable
fun MovieStats(modifier: Modifier = Modifier, movie: MovieDetail) {
    Row(modifier = modifier) {
        Text(movie.popularity.toString())
    }
}