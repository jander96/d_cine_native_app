package com.devj.dcine.features.settings.presenter.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devj.dcine.LocalSettingsViewModel
import com.devj.dcine.R
import com.devj.dcine.core.composables.MenuOptionItem
import com.devj.dcine.core.composables.MenuOptions

@Composable
fun LanguageSelector(modifier: Modifier = Modifier) {
    val settingsViewModel = LocalSettingsViewModel.current
    val currentSettings by settingsViewModel.state.collectAsState()
    val languages = listOf( "es-ES", "en-US")

    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(16.dp)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Image(painter = painterResource(getFlagFromLanguage(currentSettings.language)), contentDescription = "options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach {
                DropdownMenuItem(
                    leadingIcon = {
                        Box {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(getFlagFromLanguage(it)),
                                contentDescription = "options"
                            )
                        }
                    },
                    text = { Text(it) },
                    onClick = {
                        settingsViewModel.changeLanguage(it)
                        expanded = false
                    })
            }
        }
    }

}

private fun getFlagFromLanguage(language: String): Int {
    return when(language){
        "es-ES" -> R.drawable.es_flag
        "en-US" -> R.drawable.us_flag
        else -> R.drawable.us_flag
    }
}