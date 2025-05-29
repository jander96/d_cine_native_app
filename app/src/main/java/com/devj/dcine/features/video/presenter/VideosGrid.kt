package com.devj.dcine.features.video.presenter

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.devj.dcine.R
import com.devj.dcine.core.composables.shimmerBrush
import com.devj.dcine.core.presenter.AsyncState
import com.devj.dcine.features.video.domain.model.Video

@Composable
fun VideosGrid(modifier: Modifier = Modifier, videosState: MovieVideosState) {

    Box(modifier = modifier.height(320.dp)) {
        Column {
            Text(
                text = "Videos",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            when (videosState.asyncState) {
                AsyncState.INITIAL -> CircularProgressIndicator()
                AsyncState.LOADING -> CircularProgressIndicator()
                AsyncState.SUCCESS -> VideosGridView(modifier = Modifier, videos = videosState.videos)
                AsyncState.ERROR -> {
                    return Text(text = "Error: ${videosState.error?.message ?: "Unknown error"}")
                }
            }
        }

    }



}

@Composable
private fun VideosGridView(modifier: Modifier = Modifier, videos : List<Video>) {
    // implement a grid use LazyHorizontalGrid

    LazyHorizontalGrid(
        modifier = modifier,
        rows = GridCells.Adaptive(minSize = 128.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(videos) {
            VideoItem(video = it)
        }
    }


}
@Composable
fun VideoItem(video: Video, modifier : Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(
        video.getThumbnailUrl(),
        contentScale = ContentScale.Crop,
        )
    val state = painter.state.collectAsState()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        when (state.value) {
            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .height(180.dp)
                        .aspectRatio(16f / 9f)
                        .background(shimmerBrush()),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(strokeWidth = 1.dp, modifier = Modifier.fillMaxWidth(0.5f))
                }
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    modifier = Modifier
                        .clickable {
                            context.startActivity(
                                video.getPlaybackIntent(context, video)
                            )
                        }
                        .padding(end = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .height(180.dp)
                        .aspectRatio(16f / 9f),
                    painter = painter,
                    contentScale = ContentScale.Crop,
                    contentDescription = video.name
                )
            }

            is AsyncImagePainter.State.Error -> {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .padding(end = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .height(180.dp)
                        .aspectRatio(16f / 9f),
                    painter = painterResource(R.drawable.no_photo),
                    contentDescription = video.name
                )
            }
        }
        Text(video.name.run {
           val name = if(length > 12) "${take(12)}..." else this
            name;
        }, style = MaterialTheme.typography.bodySmall)
    }
}

private fun Video.getPlaybackIntent(context: Context, video: Video): Intent {
    return Intent(context, PlaybackActivity::class.java).apply {
        putExtra(PlaybackActivity.VIDEO, video)
    }
}
