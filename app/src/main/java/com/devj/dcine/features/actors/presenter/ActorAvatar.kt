package com.devj.dcine.features.actors.presenter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.devj.dcine.core.composables.AsyncImageShimmer
import com.devj.dcine.core.composables.shimmerBrush
import com.devj.dcine.features.detail.domain.Actor

@Composable
fun ActorAvatar(modifier: Modifier = Modifier, actor: Actor) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImageShimmer(model = actor.profilePath, contentDescription = actor.name)
        Text(actor.name, style = MaterialTheme.typography.bodySmall)
        Text("as: ${actor.character}", style = MaterialTheme.typography.bodySmall)
    }
}