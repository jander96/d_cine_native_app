package com.devj.dcine.features.video.presenter

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import androidx.media3.ui.compose.PlayerSurface
import androidx.media3.ui.compose.SURFACE_TYPE_SURFACE_VIEW
import com.devj.dcine.features.video.domain.model.Video
import com.devj.dcine.features.video.domain.model.VideoSite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(UnstableApi::class)
@Composable
fun PlaybackScreen(
    video: Video, playbackViewModel: PlaybackViewModel = koinViewModel(
        parameters = { parametersOf(video) }
    )
) {

    val isControlsVisible = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    if (video.site == VideoSite.YOUTUBE) {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {
            YouTubePlayer(videoId = video.key, modifier = Modifier)
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            // Mostrar controles al tocar
                            isControlsVisible.value = true
                            coroutineScope.launch {
                                delay(15000) // Ocultar después de 15s
                                isControlsVisible.value = false
                            }
                        }
                    )
                }
        ) {

            PlayerSurface(
                player = playbackViewModel.player,
                surfaceType = SURFACE_TYPE_SURFACE_VIEW,
                modifier = Modifier,
            )
            if (isControlsVisible.value) {
                PlayPauseButton(
                    modifier = Modifier.align(Alignment.Center),
                    onRewind = {},
                    onPlayPause = {
                        if (playbackViewModel.player.isPlaying) {
                            playbackViewModel.pauseVideo()
                        } else {
                            playbackViewModel.playVideo()
                        }
                    },
                    onFastForward = {},
                    isPlaying = false,
                )
            }
        }
    }
}

@Composable
fun TopMediaRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "S1:E1 - Pilot", color = Color.White)
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = Color.White
        )
    }
}


@Composable
fun PlayPauseButton(
    isPlaying: Boolean,
    onRewind: () -> Unit,
    onPlayPause: () -> Unit,
    onFastForward: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            onClick = onRewind,
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp),
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Rewind 10s",
                tint = Color.White
            )
        }
        IconButton(
            onClick = onPlayPause,
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp),
                imageVector = if (isPlaying)
                    Icons.Filled.Close else
                    Icons.Filled.PlayArrow,
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint = Color.White
            )
        }
        IconButton(
            onClick = onFastForward,
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp),
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Fast-forward 10s",
                tint = Color.White
            )
        }
    }
}


@Composable
fun ProgressBarWithTime(modifier: Modifier = Modifier) {
    val progress = remember { mutableStateOf(0.3f) } // Valor ficticio
    val formattedTime = "22:49" // Tiempo ficticio

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Slider(
            value = progress.value,
            onValueChange = { progress.value = it },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = formattedTime,
            color = Color.White
        )
    }
}