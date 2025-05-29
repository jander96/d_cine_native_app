package com.devj.dcine.core.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.devj.dcine.R

@Composable
fun AsyncImageShimmer(
    model: Any?,
    modifier: Modifier = Modifier,
    height: Int = 180,
    aspectRatio: Float = 1f / 1.5f,
    contentDescription: String = "Image",
    @DrawableRes errorImage: Int = R.drawable.no_photo
) {
    val painter = rememberAsyncImagePainter(model)
    val state = painter.state.collectAsState()
    Box(
        modifier = modifier,
    ) {

        when (state.value) {
            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .height(height.dp)
                        .aspectRatio(aspectRatio)
                        .background(shimmerBrush()),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        strokeWidth = 1.dp,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                }
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .height(height.dp)
                        .aspectRatio(aspectRatio),
                    painter = painter,
                    contentDescription = contentDescription
                )
            }

            is AsyncImagePainter.State.Error -> {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .padding(end = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .height(height.dp)
                        .aspectRatio(aspectRatio),
                    painter = painterResource(errorImage),
                    contentDescription = contentDescription
                )
            }
        }
    }
}