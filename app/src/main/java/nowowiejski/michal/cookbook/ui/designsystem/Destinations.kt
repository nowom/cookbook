package nowowiejski.michal.cookbook.ui.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector


data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(
        name = "Home",
        route = Destinations.RECIPE_LIST_ROUTE,
        icon = Icons.Rounded.Home,
    ),
    BottomNavItem(
        name = "Create",
        route = Destinations.RECIPE_FORM_ROUTE,
        icon = Icons.Rounded.Home,
    ),
    BottomNavItem(
        name = "Settings",
        route = Destinations.SHOPPING_LIST_ROUTE,
        icon = Icons.Rounded.Home,
    ),
)

object Destinations {
    const val RECIPE_FORM_ROUTE = "recipe_form"
    const val RECIPE_LIST_ROUTE = "recipe_list"
    const val SHOPPING_LIST_ROUTE = "shopping_list"
}

