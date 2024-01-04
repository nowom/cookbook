package nowowiejski.michal.recipedetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val recipeDetailsNavigationRoute = "recipe_details/"

fun NavController.navigateToRecipeDetails(navOptions: NavOptions? = null) {
    this.navigate(recipeDetailsNavigationRoute, navOptions)
}

fun NavGraphBuilder.recipeDetailsScreen() {
    composable(
        route = recipeDetailsNavigationRoute,
    ) {
        RecipeDetailsScreen()
    }
}
