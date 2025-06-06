package com.devj.dcine.features.wishlist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WishlistScreen(modifier: Modifier = Modifier) {
    Scaffold {
            contentPadding ->
        Text("Wishlist Screen", modifier = modifier.padding(contentPadding))
    }
}