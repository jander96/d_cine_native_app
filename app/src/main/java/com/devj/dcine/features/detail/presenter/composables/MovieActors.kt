package com.devj.dcine.features.detail.presenter.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devj.dcine.core.composables.Space8
import com.devj.dcine.features.actors.presenter.ActorAvatar
import com.devj.dcine.features.detail.domain.Actor

@Composable
fun MovieActors(modifier: Modifier = Modifier,  actors: List<Actor>) {
    Column {
        Text("Cast")
        Space8()
        LazyRow(modifier = modifier) {
            items(actors) {
                ActorAvatar(
                    modifier = modifier,
                    actor = it
                )
            }
        }
    }
}