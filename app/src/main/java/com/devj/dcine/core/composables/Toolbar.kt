package com.devj.dcine.core.composables

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.devj.dcine.core.composables.management.states.toolbar.TScrollState
import com.devj.dcine.core.composables.management.states.toolbar.ToolbarState
import kotlin.properties.Delegates


@Composable
fun CollapsingToolbar(
    backgroundImage: String,
    modifier: Modifier = Modifier,
    toolbarState: ToolbarState,
    onBackClick: () -> Unit = {},
    title: @Composable () -> Unit = { },
) {
    var observableValue by Delegates.observable("") { _, prev, current ->

    }
    Surface(
        shadowElevation = 4.dp,
        tonalElevation = 4.dp,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .height(with(LocalDensity.current) { toolbarState.height.toDp() }),
    ) {
        val fontSize by remember {
            derivedStateOf { (28 * toolbarState.progress).coerceIn(20f, 28f) }
        }

        val isVisible by remember {
            derivedStateOf { toolbarState.progress > .5f }
        }

        val opacity by animateFloatAsState(
            targetValue = if (isVisible) 1f else 0f,
            animationSpec = tween(durationMillis = 300)
        )
        val color by animateColorAsState(
            targetValue = if (isVisible) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            animationSpec = tween(durationMillis = 300)
        )
        Box(
            contentAlignment = Alignment.BottomStart
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationY = toolbarState.offset
                        alpha = toolbarState.progress
                    },
                model = ImageRequest.Builder(LocalContext.current).data(backgroundImage)
                    .crossfade(true).build(),
                contentDescription = "Toolbar background image",
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(opacity)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = .5f)),
                            startY = toolbarState.height * .7f,
                            endY = toolbarState.height
                        )
                    )
            )

            ToolbarLayout(
                position = toolbarState.progress,
                modifier = Modifier
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    ProvideTextStyle(
                        value = TextStyle(
                            fontSize = fontSize.sp,
                            color = color
                        )
                    ) {
                        title()
                    }
                }

                Box(
                    modifier = Modifier
                ) {
                    IconButton(
                        onClick = onBackClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .5f)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            }


        }
    }
}

@Composable
fun ToolbarLayout(
    modifier: Modifier = Modifier,
    position: Float = 0f,
    content: @Composable () -> Unit,
) {
    val statusBarInsets = WindowInsets.safeDrawing
    val density = LocalDensity.current


    Layout(
        content = {
            content()
        },
        modifier = modifier
    ) { measurables, constraints ->
        val titleMeasurable = measurables[0]
        val backIconMeasurable = measurables[1]

        val titlePlaceable = titleMeasurable.measure(constraints)
        val backIconPlaceable = backIconMeasurable.measure(constraints)

        val containerHeight = constraints.maxHeight

        val startYTitle = containerHeight - titlePlaceable.height
        val startYIcon = statusBarInsets.getTop(density)

        val endYTitle = ((containerHeight * .6)).toInt()
        val endYIcon = ((containerHeight * .6) - (titlePlaceable.height * .6)).toInt()

        val titleY = lerp(startYTitle, endYTitle, 1 - position)
        val iconY = lerp(startYIcon, endYIcon, 1 - position)

        layout(constraints.maxWidth, containerHeight) {
            titlePlaceable.placeRelative(
                x = lerp(
                    start = backIconPlaceable.width + 8,
                    stop = 0,
                    fraction = position

                ).toInt(),
                y = titleY
            )
            backIconPlaceable.placeRelative(
                x = 0,
                y = iconY
            )
        }
    }
}


@Preview
@Composable
private fun ExpandedToolbarPreview() {
    CollapsingToolbar(
        backgroundImage = "https://miro.medium.com/v2/resize:fit:1400/format:webp/1*zx5Ko6jnXmPh3JpxIbP86w.png",
        toolbarState = TScrollState(56..300, scrollValue = 0),
        title = { Text("Title") })
}

@Preview
@Composable
private fun CollapsedToolbarPreview() {
    CollapsingToolbar(
        backgroundImage = "https://miro.medium.com/v2/resize:fit:1400/format:webp/1*zx5Ko6jnXmPh3JpxIbP86w.png",
        toolbarState = TScrollState(56..300, scrollValue = 250),
        title = { Text("Title") })
}