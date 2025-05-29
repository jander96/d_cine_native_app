package com.devj.dcine.features.detail.presenter.composables


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devj.dcine.features.detail.domain.MovieDetail
import com.devj.dcine.features.detail.mockMovieDetail

@Composable
fun MovieOverview(modifier: Modifier = Modifier, movie: MovieDetail) {
    Column(modifier = modifier,
        ) {
        Text("Overview", style = MaterialTheme.typography.titleLarge)
        Text(movie.overview)
    }
}

@Preview
@Composable
private fun MovieTitlePreview() {

    MovieOverview(movie = mockMovieDetail)

}