package com.devj.dcine

import android.content.Context
import android.content.Intent
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
import androidx.lifecycle.lifecycleScope
import com.devj.dcine.features.auth.presenter.AuthenticationViewModel
import com.devj.dcine.features.auth.presenter.UserViewModel
import com.devj.dcine.features.settings.presenter.SettingsViewModel
import com.devj.dcine.ui.theme.DCineTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    lateinit var authViewModel: AuthenticationViewModel
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        intent.data?.let { uri ->
            if (uri.scheme == "dcine" && uri.host == "auth" && uri.path == "/callback") {
                val token = uri.getQueryParameter("request_token")
                val isDenied = uri.getQueryParameter("denied").toBoolean()
                if (isDenied) {
                    authViewModel.onAuthDenied()
                    return
                }
                if (!token.isNullOrEmpty()) {

                    lifecycleScope.launch {
                        authViewModel.createSession(token)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            authViewModel = koinViewModel()
            val settingsViewModel: SettingsViewModel = koinViewModel()
            val userViewModel: UserViewModel = koinViewModel()
            val state = settingsViewModel.state.collectAsStateWithLifecycle()
            val locale = when (state.value.language) {
                "es-ES" -> Locale("es")
                "en-US" -> Locale("en")
                else -> Locale.getDefault()
            }

            LocalizedApp(locale = locale) {
                CompositionLocalProvider(LocalSettingsViewModel provides settingsViewModel) {

                    CompositionLocalProvider(LocalUserViewModel provides userViewModel) {
                        DCineTheme {
                            App(authViewModel = authViewModel)
                        }
                    }

                }
            }
        }
    }
}

val LocalSettingsViewModel = staticCompositionLocalOf<SettingsViewModel> {
    error("No AppSettingsViewModel provided")
}

val LocalUserViewModel = staticCompositionLocalOf<UserViewModel> {
    error("No UserViewModel provided")
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
