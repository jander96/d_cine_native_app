package com.devj.dcine.features.search.presenter.composables

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
import kotlin.collections.forEach

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenresFilter(
    modifier: Modifier = Modifier,
    genres: List<Genre>,
    onGenreSelected: (List<Genre>) -> Unit = { _ -> },
    selectedGenres: List<Genre> = emptyList()
) {

    FlowRow(modifier = modifier) {
        genres.forEach { genre ->
            GenreTag(
                tag = genre.name,
                modifier = Modifier.padding(horizontal = 4.dp),
                isSelected = genre in selectedGenres,
                onClick = {
                    val updatedGenres = if (genre in selectedGenres) {
                        selectedGenres - genre
                    } else {
                        selectedGenres + genre
                    }
                    onGenreSelected(updatedGenres)
                }
            )
        }
    }

}

@Composable
fun GenreTag(
    modifier: Modifier = Modifier,
    tag: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    FilterChip(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(.5.dp, MaterialTheme.colorScheme.primary),
        modifier = modifier,
        selected = isSelected,
        label = { Text(tag) },
        onClick = onClick
    )
}