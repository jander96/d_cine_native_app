package com.devj.dcine.features.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Scaffold {
        contentPadding ->
        Text("Profile Screen", modifier = modifier.padding(contentPadding))
    }
}