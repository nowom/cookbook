package nowowiejski.michal.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation


const val homeNavigationRoute = "home_route"
const val HOME_GRAPH_ROUTE_PATTERN = "home_graph"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HOME_GRAPH_ROUTE_PATTERN, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onRecipeClick: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = HOME_GRAPH_ROUTE_PATTERN,
        startDestination = homeNavigationRoute,
    ) {
        composable(route = homeNavigationRoute) {
            HomeRoute(onRecipeClick)
        }
        nestedGraphs()
    }

}
