package com.devj.dcine

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devj.dcine.features.settings.presenter.SettingsViewModel
import com.devj.dcine.ui.theme.DCineTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = koinViewModel()
            val state = settingsViewModel.state.collectAsStateWithLifecycle()
            val locale = when (state.value.language) {
                "es-ES" -> Locale("es")
                "en-US" -> Locale("en")
                else -> Locale.getDefault()
            }

            LocalizedApp(locale = locale) {
                CompositionLocalProvider(LocalSettingsViewModel provides settingsViewModel) {

                    DCineTheme {
                        App()
                    }

                }
            }
        }
    }
}

val LocalSettingsViewModel = staticCompositionLocalOf<SettingsViewModel> {
    error("No AppSettingsViewModel provided")
}
val LocalAppLocale = compositionLocalOf { Locale.getDefault() }


fun Context.wrapWithLocale(locale: Locale): Context {
    val config = Configuration(resources.configuration)
    Locale.setDefault(locale)
    config.setLocale(locale)
    return createConfigurationContext(config)
}

@Composable
fun LocalizedApp(locale: Locale, content: @Composable () -> Unit) {
    val context = LocalContext.current
    val localizedContext = remember(locale) { context.wrapWithLocale(locale) }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalAppLocale provides locale
    ) {
        content()
    }
}
