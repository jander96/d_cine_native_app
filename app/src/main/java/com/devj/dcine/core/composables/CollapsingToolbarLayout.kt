package com.devj.dcine.core.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.util.lerp
import kotlin.math.roundToInt

@Composable
private fun CollapsingToolbarLayout(
    progress: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 5) // [0]: Country Map | [1-3]: Logo Images | [4]: Buttons

        val placeables = measurables.map {
            it.measure(constraints)
        }
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {

            val expandedHorizontalGuideline = (constraints.maxHeight * 0.4f).roundToInt()
            val collapsedHorizontalGuideline = (constraints.maxHeight * 0.5f).roundToInt()

            val countryMap = placeables[0]
            val costa = placeables[1]
            val rica = placeables[2]
            val wildlife = placeables[3]
            val buttons = placeables[4]
            countryMap.placeRelative(
                x = 0,
                y = collapsedHorizontalGuideline - countryMap.height / 2,
            )
            costa.placeRelative(
                x = lerp(
                    start = countryMap.width,
                    stop = constraints.maxWidth / 2 - costa.width,
                    fraction = progress
                ),
                y = lerp(
                    start = collapsedHorizontalGuideline - costa.height / 2,
                    stop = expandedHorizontalGuideline - costa.height,
                    fraction = progress
                )
            )
            rica.placeRelative(
                x = lerp(
                    start = countryMap.width + costa.width,
                    stop = constraints.maxWidth / 2 - rica.width,
                    fraction = progress
                ),
                y = lerp(
                    start = collapsedHorizontalGuideline - rica.height / 2,
                    stop = expandedHorizontalGuideline,
                    fraction = progress
                )
            )
            wildlife.placeRelative(
                x = lerp(
                    start = countryMap.width + costa.width + rica.width,
                    stop = constraints.maxWidth / 2,
                    fraction = progress
                ),
                y = lerp(
                    start = collapsedHorizontalGuideline - wildlife.height / 2,
                    stop = expandedHorizontalGuideline + rica.height / 2,
                    fraction = progress
                )
            )
            buttons.placeRelative(
                x = constraints.maxWidth - buttons.width,
                y = lerp(
                    start = (constraints.maxHeight - buttons.height) / 2,
                    stop = 0,
                    fraction = progress
                )
            )
        }
    }
}