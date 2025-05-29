package com.devj.dcine.features.auth.presenter.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun UserAvatar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.size(40.dp),
        shape = CircleShape,
        shadowElevation = 4.dp,
        tonalElevation = 2.dp,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        val placeHolder = "https://upload.wikimedia.org/wikipedia/en/8/86/Avatar_Aang.png"

        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current).data(placeHolder)
                .crossfade(true).build(),
            contentDescription = "Toolbar background image",
            contentScale = ContentScale.Crop
        )
    }
}