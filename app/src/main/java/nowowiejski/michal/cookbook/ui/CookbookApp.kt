package nowowiejski.michal.cookbook.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import nowowiejski.michal.cookbook.ui.navigation.CookbookNavHost
import nowowiejski.michal.designsystem.theme.CookbookTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CookbookApp(
    appState: AppState = navigationAppState(),
) {
    val navController = rememberNavController()
    //val primaryColor = Color(0xFF76985C) // Zielony
    //val primaryColor = Color(0xFFE8BB6B) // Pomarancz
    val primaryColor = Color(0xFFF0F4EF) // zielen lekka

    //val primaryColor = Color(0xFF66517C) // Fioletowy
    //val primaryColor = Color(0xFF644E7C) // Fioletowy

    val accentColor = Color(0xFFFFD700) // Złoty
    CookbookTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("My Cookbook", style = MaterialTheme.typography.bodyMedium) },
                    actions = {
                        IconButton(onClick = { /* TODO: Profile/Settings */ }) {
                            Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                        }
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = Color.White,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                MainBottomMenu(
                    destinations = appState.topLevelDestinations,
                    currentDestination = appState.currentDestination,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                )
            },
        ) { padding ->
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                CookbookNavHost(appState)
            }
        }
    }
}