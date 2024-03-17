package nowowiejski.michal.cookbook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import nowowiejski.michal.cookbook.ui.AppState
import nowowiejski.michal.feature.navigateToNewIngredientsForm
import nowowiejski.michal.feature.newIngredientsScreen
import nowowiejski.michal.feature.newRecipeNavigationRoute
import nowowiejski.michal.feature.recipeFormScreen
import nowowiejski.michal.home.homeScreen
import nowowiejski.michal.home.navigateToHome
import nowowiejski.michal.recipedetails.navigateToRecipeDetails
import nowowiejski.michal.recipedetails.recipeDetailsScreen
import nowowiejski.michal.shopping.shoppingListScreen

@Composable
internal fun CookbookNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = newRecipeNavigationRoute,
        modifier = modifier,
    ) {
        recipeFormScreen(onIngredientClick = navController::navigateToNewIngredientsForm,
            onRecipeSubmit = navController::navigateToHome,
            nestedGraphs = {
                newIngredientsScreen(
                    onNavigateUp = navController::popBackStack,
                    onIngredientsClick = {

                    }
                )
            })
        shoppingListScreen()
        homeScreen(onRecipeClick = {
            navController.navigateToRecipeDetails()
        }, nestedGraphs = {
        })
        recipeDetailsScreen()
    }
}