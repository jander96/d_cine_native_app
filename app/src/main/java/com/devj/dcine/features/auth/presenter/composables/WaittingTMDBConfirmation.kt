package com.devj.dcine.features.auth.presenter.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devj.dcine.core.composables.Space4
import com.devj.dcine.core.composables.Space40
import com.devj.dcine.core.composables.Space8

@Composable
fun WaitingTMDBConfirmation(modifier: Modifier = Modifier, onCancel: () -> Unit) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Space40()
        Space40()
        Text(
            "Waiting for web confirmation",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
        )
        Space4()
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
        Space4()
        FilledTonalButton(onClick = onCancel) {
            Text("Cancel")
        }
    }


}