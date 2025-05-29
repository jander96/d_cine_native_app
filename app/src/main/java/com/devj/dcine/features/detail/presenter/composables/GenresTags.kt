package com.devj.dcine.features.detail.presenter.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devj.dcine.features.detail.domain.Genre

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenresTags(
    modifier: Modifier = Modifier,
    genres: List<Genre>
) {

    FlowRow(modifier = modifier) {
        genres.forEach { genre ->
            GenreTag(
                tag = genre.name,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }

}

@Composable
fun GenreTag(modifier: Modifier = Modifier, tag: String) {
    FilterChip(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(.5.dp, MaterialTheme.colorScheme.primary),
        modifier = modifier,
        selected = false,
        label = { Text(tag) },
        onClick = {

        }
    )
}