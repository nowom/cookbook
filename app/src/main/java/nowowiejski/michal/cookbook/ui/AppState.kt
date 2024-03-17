package nowowiejski.michal.cookbook.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import nowowiejski.michal.cookbook.ui.navigation.BottomNavItem
import nowowiejski.michal.cookbook.ui.navigation.Destinations
import nowowiejski.michal.cookbook.ui.navigation.bottomNavItems
import nowowiejski.michal.feature.navigateToRecipeForm
import nowowiejski.michal.home.navigateToHome
import nowowiejski.michal.shopping.navigateToShoppingList

@Composable
internal fun navigationAppState(
    navController: NavHostController = rememberNavController()
): AppState {
    return remember(navController) {
        AppState(navController)
    }
}

@Stable
internal class AppState(
    val navController: NavHostController
) {

    val topLevelDestinations: List<BottomNavItem> = bottomNavItems

//    val currentTopLevelDestination: TabsDestinations?
//        @Composable get() = when (currentDestination?.route) {
//            dashboardNavigationRoute -> TabsDestinations.Dashboard
//            else -> null
//        }

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun navigateToTopLevelDestination(navItem: BottomNavItem) {
        navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
        when (navItem.route) {
            Destinations.RECIPE_FORM_ROUTE -> navController.navigateToRecipeForm()
            Destinations.SHOPPING_LIST_ROUTE -> navController.navigateToShoppingList()
            Destinations.RECIPE_LIST_ROUTE -> navController.navigateToHome()
        }

    }
}