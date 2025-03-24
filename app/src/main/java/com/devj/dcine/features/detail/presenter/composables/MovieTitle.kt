package com.devj.dcine.features.detail.presenter.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.detail.mockMovieDetail
import com.devj.dcine.features.detail.presenter.MovieDetailView

@Composable
fun MovieTitle(modifier: Modifier = Modifier, movie: MovieDetail) {
    Row(modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
        ) {
        Text(movie.title)
        Row {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Icon(imageVector = Icons.Default.Share, contentDescription = null)
        }
    }
}

@Preview
@Composable
private fun MovieTitlePreview() {

    MovieTitle(movie = mockMovieDetail)

}