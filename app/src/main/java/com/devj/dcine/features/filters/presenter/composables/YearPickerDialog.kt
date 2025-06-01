package com.devj.dcine.features.filters.presenter.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun YearPickerDialog(
    modifier : Modifier = Modifier,
    initialYear: Int = LocalDate.now().year,
    fromYear: Int = 1900,
    toYear: Int = LocalDate.now().year,
    onYearSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Selecciona un año") },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 64.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .height(400.dp),
            ) {
                items(toYear - fromYear + 1) { index ->
                    val year = toYear - index
                    Surface(
                        modifier = Modifier
                            .clickable {
                                onYearSelected(year)
                                onDismiss()
                            }
                            .fillMaxWidth()
                            .padding(4.dp),
                        shape = MaterialTheme.shapes.small,
                        shadowElevation = 1.dp,
                        color = if (year == initialYear) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                        onClick = {
                            onYearSelected(year)
                            onDismiss()
                        },
                        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
                    ) {
                        Text(
                            text = year.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp)

                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}