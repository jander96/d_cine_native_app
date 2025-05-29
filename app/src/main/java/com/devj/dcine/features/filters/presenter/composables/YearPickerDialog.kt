package com.devj.dcine.features.filters.presenter.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun YearPickerDialog(
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
            LazyColumn {
                items(toYear - fromYear + 1) { index ->
                    val year = toYear - index
                    Text(
                        text = year.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onYearSelected(year)
                                onDismiss()
                            }
                            .padding(16.dp)
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}