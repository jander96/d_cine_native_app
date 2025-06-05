package com.devj.dcine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devj.dcine.navigation.Screen
import com.devj.dcine.navigation.mainGraph
import com.devj.dcine.navigation.rememberNavigationController
import java.nio.file.WatchEvent


@Composable
fun App(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val navigationController = rememberNavigationController(navController)
    val destinations = listOf(
        MainDestination(
            screen = Screen.Home,
            label = "Home",
            icon = { isSelected ->
                if (isSelected) {
                    Image(
                        painter = painterResource(id = R.drawable.home_beauty),
                        contentDescription = "Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.home_beauty),
                        contentDescription = "Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.6f
                            )
                        )
                    )
                }
            }
        ),
        MainDestination(
            screen = Screen.Search, // Placeholder, will be replaced in detail screen
            label = "Search",
            icon = { isSelected ->
                if (isSelected) {
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.6f
                            )
                        )
                    )
                }
            }
        ),
        MainDestination(
            screen = Screen.Wishlist,
            label = "Whishlist",
            icon = { isSelected ->
                if (isSelected) {
                    Image(
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = "Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = "Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.6f
                            )
                        )
                    )
                }
            }
        ),
        MainDestination(
            screen = Screen.Profile,
            label = "Profile",
            icon = { isSelected ->
                if (isSelected) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.6f
                            )
                        )
                    )
                }
            }
        )
    )

    Column(modifier = modifier.fillMaxSize()) {
        NavHost(
            modifier = Modifier.weight(1f),
            navController = navController,
            startDestination = Screen.Splash,
        ) {
            mainGraph(navigationController)
        }
        AnimatedVisibility(
            visible = navigationController.currentDestination.value in destinations.map { it.screen },
        ) {
            BottomNavigationBar(
                modifier = Modifier,
                destinations = destinations,
                selectedDestination = navigationController.currentDestination.value,
                onDestinationClick = {
                    navigationController.navigateTopSingle(it)
                }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    destinations: List<MainDestination> = emptyList(),
    onDestinationClick: (Screen) -> Unit = {},
    selectedDestination: Screen,
) {


    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (dst in destinations) {
            Column(
                modifier = Modifier
                    .clickable {
                    onDestinationClick(dst.screen)
                }.padding(top = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                dst.icon(dst.screen == selectedDestination)
                Text(
                    dst.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (dst.screen == selectedDestination) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    }
                )
            }

        }
    }

}

data class MainDestination(
    val screen: Screen,
    val label: String,
    val icon: @Composable (isSelected: Boolean) -> Unit
)