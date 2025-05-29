package com.devj.dcine.features.detail.presenter.composables

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.ImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.devj.dcine.R
import com.devj.dcine.features.detail.domain.MovieDetail

@Composable
fun MovieCompanies(modifier: Modifier = Modifier, movie: MovieDetail) {
    Column(modifier = modifier) {

        Text("Companies", style = MaterialTheme.typography.titleLarge)
        LazyRow {
            items(movie.productionCompanies) {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .fillMaxHeight()
                        .clip(MaterialTheme.shapes.large)
                ) {
                    AsyncImage(
                        placeholder =  painterResource(R.drawable.movie_film),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it.logo)
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.DISABLED)
                            .build(),
                        contentDescription = it.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.height(60.dp)
                    )
                }
            }
        }
    }
}