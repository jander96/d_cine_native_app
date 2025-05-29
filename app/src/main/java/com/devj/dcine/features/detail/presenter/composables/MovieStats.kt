package com.devj.dcine.features.detail.presenter.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.LayoutDirection
import com.devj.dcine.core.composables.Space16
import com.devj.dcine.core.utils.extensions.compact
import com.devj.dcine.core.utils.extensions.toStringWithDecimal
import com.devj.dcine.features.detail.domain.MovieDetail


@Composable
fun MovieStats(modifier: Modifier = Modifier, movie: MovieDetail) {
    val resolver = LocalFontFamilyResolver.current
    val density = LocalDensity.current
    val textMeasurer = TextMeasurer(
        defaultFontFamilyResolver = resolver,
        defaultDensity = density,
        defaultLayoutDirection = LayoutDirection.Ltr,
    )
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "${movie.voteCount.compact()} votes")
        Space16()
        CircularProgressIndicator(
            progress = { movie.voteAverage.toFloat() / 10f },
            modifier = Modifier.drawBehind {
                val text = movie.voteAverage.toStringWithDecimal(1)

                val textLayoutResult = textMeasurer.measure(text)
                val textWidth = textLayoutResult.size.width
                val textHeight = textLayoutResult.size.height
                val centerX = (size.width - textWidth) / 2
                val centerY = (size.height - textHeight) / 2
                drawText(
                    textMeasurer = textMeasurer,
                    text = text,
                    topLeft = Offset(centerX, centerY)
                )
            })
    }
}

