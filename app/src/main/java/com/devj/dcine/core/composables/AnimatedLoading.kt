package com.devj.dcine.core.composables

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun AnimatedLoading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier)
}