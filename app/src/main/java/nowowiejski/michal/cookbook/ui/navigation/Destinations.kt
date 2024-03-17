package nowowiejski.michal.cookbook.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.ShoppingCart
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
        icon = Icons.Rounded.Create,
    ),
    BottomNavItem(
        name = "Shopping list",
        route = Destinations.SHOPPING_LIST_ROUTE,
        icon = Icons.Rounded.ShoppingCart,
    ),
    BottomNavItem(
        name = "My pantry",
        route = Destinations.SHOPPING_LIST_ROUTE,
        icon = Icons.Rounded.Menu,
    ),
)

object Destinations {
    const val RECIPE_FORM_ROUTE = "recipe_form"
    const val RECIPE_LIST_ROUTE = "recipe_list"
    const val SHOPPING_LIST_ROUTE = "shopping_list"
}

