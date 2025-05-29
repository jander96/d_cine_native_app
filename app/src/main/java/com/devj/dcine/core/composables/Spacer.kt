package com.devj.dcine.core.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.Space4(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(4.dp))
}

@Composable
fun RowScope.Space4(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.width(4.dp))
}

@Composable
fun ColumnScope.Space8(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(8.dp))
}

@Composable
fun RowScope.Space8(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.width(8.dp))
}

@Composable
fun ColumnScope.Space16(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(16.dp))
}

@Composable
fun RowScope.Space16(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.width(16.dp))
}

@Composable
fun ColumnScope.Space32(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(32.dp))
}

@Composable
fun RowScope.Space32(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.width(32.dp))
}

@Composable
fun ColumnScope.Space40(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(40.dp))
}

@Composable
fun RowScope.Space40(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.width(40.dp))
}