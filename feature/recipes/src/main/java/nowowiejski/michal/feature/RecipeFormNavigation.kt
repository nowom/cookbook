package nowowiejski.michal.feature

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val newRecipeNavigationRoute = "add_recipe_route/"

fun NavController.navigateToRecipeForm(navOptions: NavOptions? = null) {
    this.navigate(newRecipeNavigationRoute, navOptions)
}

fun NavGraphBuilder.recipeFormScreen() {
    composable(
        route = newRecipeNavigationRoute,
    ) {
        RecipeFormRoute()
    }
}
