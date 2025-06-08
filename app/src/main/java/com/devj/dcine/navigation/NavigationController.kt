package com.devj.dcine.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController

@Composable
fun rememberNavigationController(
    navController: NavController,
    initialScreen: Screen
): Navigator {
    return remember(navController) {
        Navigator(navController, initialScreen)
    }
}

class Navigator(private val navController: NavController, initialScreen: Screen) {

    var currentDestination: MutableState<Screen> = mutableStateOf(initialScreen)
        private set

    val navStack = mutableListOf<Screen>().apply {
    }

    private fun updateScreen(screen: Screen) {
        currentDestination.value = screen
        if (!navStack.contains(screen)) {
            navStack.add(screen)
        } else {
            navStack.remove(screen)
            navStack.add(screen)
        }
    }

    fun navigateTopSingle(screen: Screen) {
        navController.navigate(screen) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        updateScreen(screen)
    }

    fun navigate(screen: Screen) {
        navController.navigate(screen)
        updateScreen(screen)
    }

    fun popBackStack() {
        navController.popBackStack()
        if (navStack.isNotEmpty()) {
            navStack.removeAt(navStack.size - 1)
        }
        if (navStack.isNotEmpty()) {
            currentDestination.value = navStack.last()
        } else {
            currentDestination.value = Screen.Home
        }

    }
}