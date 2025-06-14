package com.devj.dcine.core.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class MenuOptionItem<T>(
    val value: T,
    val title: String,
    val icon: @Composable () -> Unit,
)

@Composable
fun<T> MenuOptions(
    selected: T,
    modifier: Modifier = Modifier,
    options: List<MenuOptionItem<T>> = emptyList(),
    onClick: (T) -> Unit = {},
    @DrawableRes icon: Int
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(16.dp)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(painter = painterResource(icon), contentDescription = "options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    leadingIcon = {
                        if (it.value == selected) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null)
                        }

                    },
                    text = { Text(it.title) },
                    onClick = {
                        onClick(it.value)
                        expanded = false
                    })
            }
        }
    }
}