package com.devj.dcine.core.composables

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devj.dcine.R

enum class StarType {
    Full, Half, Empty
}

@Composable
fun StarRating(
    rating: Double,
    modifier: Modifier = Modifier,
    maxRate : Int = 8,
    onSelect: (Double) -> Unit = {}
) {

    Row(modifier = modifier) {
        val fullStar = rating.toInt()
        val halfStar = if((rating %1) <= 0.5) 1 else 0
        repeat(fullStar) {
            Star(starType = StarType.Full)
        }
        if(halfStar != 0)
            Star(starType = StarType.Half)
        repeat(maxRate - fullStar - halfStar) {
            Star(starType = StarType.Empty)
        }
    }

}

@Composable
fun Star(modifier: Modifier = Modifier, starType: StarType = StarType.Empty) {
    var scale by remember { mutableFloatStateOf(1f) }
    Icon(
        painter = when (starType) {
            StarType.Full -> painterResource(R.drawable.fill_star)
            StarType.Half -> painterResource(R.drawable.half_star)
            StarType.Empty -> painterResource(R.drawable.empty_star)
        },
        contentDescription = null,
        modifier = modifier.size(24.dp)
            .pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    scale = if(scale == 1f) 2f else 1f
                }
            )
        }.graphicsLayer {
            scaleX = scale
                scaleY = scale
            },
        tint = Color.Gray
    )
}