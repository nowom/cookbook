package nowowiejski.michal.feature

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import nowowiejski.michal.feature.ingredients.AddIngredientsScreen

const val newRecipeNavigationRoute = "add_recipe_route/"
const val newIngredientsNavigationRoute = "add_ingredients_route"

fun NavController.navigateToRecipeForm(navOptions: NavOptions? = null) {
    this.navigate(newRecipeNavigationRoute, navOptions)
}

fun NavGraphBuilder.recipeFormScreen(
    nestedGraphs: NavGraphBuilder.() -> Unit,
    onIngredientClick: () -> Unit,
    onRecipeSubmit: () -> Unit,
) {
    composable(
        route = newRecipeNavigationRoute,
    ) {
        RecipeFormRoute(onIngredientClick, onRecipeSubmit)
    }
    nestedGraphs()
}

fun NavController.navigateToNewIngredientsForm(navOptions: NavOptions? = null) {
    this.navigate(newIngredientsNavigationRoute, navOptions)
}

fun NavGraphBuilder.newIngredientsScreen(
    onIngredientsClick: (String) -> Unit,
    onNavigateUp: () -> Unit,
) {
    composable(
        route = newIngredientsNavigationRoute,
    ) {
        AddIngredientsScreen(onNavigateUp = onNavigateUp)
    }
}
