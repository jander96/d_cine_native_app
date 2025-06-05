package com.devj.dcine.features.splash.presenter

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    splashViewModel: SplashViewModel = koinViewModel(),
    onLoadEnd: ()-> Unit,
    ) {

    LaunchedEffect(Unit) {
        splashViewModel.events.collect {
            when(it){
                SplashViewModel.UiEvent.PreviousSettings -> onLoadEnd()
                SplashViewModel.UiEvent.SetDefaultSetting -> onLoadEnd()
            }
        }
    }

    Box {
        Text("Splash")
    }

}