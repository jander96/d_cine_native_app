package com.devj.dcine

import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devj.dcine.navigation.Screen
import com.devj.dcine.navigation.mainGraph


@Composable
fun App(
    modifier: Modifier = Modifier,
    widowIfo : WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    val navController = rememberNavController()
    val windowsSize = widowIfo.windowSizeClass

        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Screen.Home,
        ) {
            mainGraph(navController)
        }
}