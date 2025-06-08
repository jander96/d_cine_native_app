package com.devj.dcine.features.auth.presenter.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devj.dcine.R
import com.devj.dcine.core.composables.Space32
import com.devj.dcine.core.composables.Space40
import com.devj.dcine.core.composables.Space8
import com.devj.dcine.ui.theme.Yellow

@Composable
fun RequestAuthWithTMDB(modifier: Modifier = Modifier, onAuthClick: () -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Space40()
        Space40()

        Text(
            "Welcome to movie app",
            style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
        )
        Space8()
        Text(
            "Click authenticate to login in TMDB web",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary)
        )

        Space40()

        FilledTonalButton(onClick = onAuthClick) {
            Text("Authenticate")
        }
        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier.size(84.dp),
            painter = painterResource(R.drawable.tmdb_transparent_logo),
            contentDescription = "logo"
        )
        Space32()


    }
}