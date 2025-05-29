package com.devj.dcine.core.composables.responsive

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable

enum class ScreenSize {
    Compact, Medium, Expanded
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun WindowAdaptiveInfo.widthSize(): ScreenSize {
    val activity: Activity? = LocalActivity.current
    return activity?.let {
        val windowSize = calculateWindowSizeClass(it)

        return when (windowSize.widthSizeClass) {
            WindowWidthSizeClass.Compact -> ScreenSize.Compact

            WindowWidthSizeClass.Medium -> ScreenSize.Medium

            WindowWidthSizeClass.Expanded -> ScreenSize.Expanded

            else -> ScreenSize.Compact
        }

    } ?: ScreenSize.Compact
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun WindowAdaptiveInfo.heightSize(): ScreenSize {
    val activity: Activity? = LocalActivity.current
    return activity?.let {
        val windowSize = calculateWindowSizeClass(it)

        return when (windowSize.heightSizeClass) {
            WindowHeightSizeClass.Compact -> ScreenSize.Compact

            WindowHeightSizeClass.Medium -> ScreenSize.Medium

            WindowHeightSizeClass.Expanded -> ScreenSize.Expanded

            else -> ScreenSize.Compact
        }

    } ?: ScreenSize.Compact
}






