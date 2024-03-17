package nowowiejski.michal.cookbook.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import nowowiejski.michal.cookbook.ui.navigation.BottomNavItem
import nowowiejski.michal.cookbook.ui.navigation.isTopLevelDestinationInHierarchy

@Composable
fun MainBottomMenu(
    destinations: List<BottomNavItem>,
    currentDestination: NavDestination?,
    onNavigateToDestination: (BottomNavItem) -> Unit,
) {
    NavigationBar(
        tonalElevation = 3.dp,
        contentColor = Color.Red,
        containerColor = Color.White,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = null,
                    )
                },
                label = { Text(destination.name) },
            )
        }
    }
}