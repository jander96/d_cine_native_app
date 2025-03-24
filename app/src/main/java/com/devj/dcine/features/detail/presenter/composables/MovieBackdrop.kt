package com.devj.dcine.features.detail.presenter.composables

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import com.devj.dcine.R
import com.devj.dcine.features.detail.domain.MovieDetail

@Composable
fun MovieBackdrop(modifier: Modifier = Modifier, movie: MovieDetail) {
    AsyncImage(
        modifier = modifier
            .aspectRatio(16f/9f),
        model = ImageRequest.Builder(LocalContext.current).data(movie.backdropPath)
            .crossfade(true)
            .placeholder(R.drawable.ic_launcher_background)
            .build(),
        contentDescription = movie.title,
        contentScale = ContentScale.Crop
    )
}