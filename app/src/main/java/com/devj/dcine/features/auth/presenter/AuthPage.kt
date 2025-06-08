package com.devj.dcine.features.auth.presenter

import android.content.Context
import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.devj.dcine.R
import com.devj.dcine.core.composables.AnimatedLoading
import com.devj.dcine.core.composables.Space40
import com.devj.dcine.core.composables.Space8
import com.devj.dcine.core.composables.rememberGasmonicBrush
import com.devj.dcine.features.auth.presenter.composables.RequestAuthWithTMDB
import com.devj.dcine.features.auth.presenter.composables.WaitingTMDBConfirmation

@Composable
fun AuthPage(
    viewModel: AuthenticationViewModel, onSuccess: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalActivity.current?.applicationContext ?: LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                AuthenticationViewModel.UiEvent.NoValidToken -> snackbarHostState.showSnackbar("Invalid token")
                is AuthenticationViewModel.UiEvent.SuccessToken -> launchIntent(context, event.url)
                is AuthenticationViewModel.UiEvent.SuccessSession -> onSuccess()
            }
        }
    }
    Box(
        modifier = Modifier.background(
            Brush.linearGradient(
                colors = listOf(
                    Color(0xFF0F2027),
                    Color(0xFF203A43),
                )
            )
        )
    ) {
        Scaffold(
            modifier = Modifier,
            containerColor = Color.Transparent,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { padding ->
            val viewState = state.value

            Box(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {

                when (viewState) {
                    is AuthenticationViewModel.State.Error -> ErrorView(onRetry = { viewModel.requestToken() })
                    AuthenticationViewModel.State.Initial -> RequestAuthWithTMDB(
                        onAuthClick = { viewModel.requestToken() })

                    AuthenticationViewModel.State.Loading -> Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        AnimatedLoading()
                    }

                    is AuthenticationViewModel.State.SuccessSession -> {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "success")
                        }
                    }

                    is AuthenticationViewModel.State.SuccessToken -> {
                        WaitingTMDBConfirmation{
                            viewModel.onAuthDenied()
                        }
                    }
                }

            }

        }
    }


}

@Composable
fun ErrorView(modifier: Modifier = Modifier, onRetry: () -> Unit) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Space40()
        Space40()
        Text(
            "User not authenticated",
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
        )
        Space8()
        FilledTonalButton(onClick = onRetry) {
            Text("Retry")
        }
    }
}


@Composable
fun LoadingAnimation(modifier: Modifier = Modifier,  onCancel: ()->Unit) {
    CircularProgressIndicator(modifier = modifier)
}


private fun launchIntent(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    // Add this line to handle calls from non-Activity contexts
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}