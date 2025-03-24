package com.devj.dcine.features.detail.presenter.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlayButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {

    ElevatedButton(modifier = modifier, onClick = onClick) {
        Row {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play")
            Text("Play")
        }
    }
}