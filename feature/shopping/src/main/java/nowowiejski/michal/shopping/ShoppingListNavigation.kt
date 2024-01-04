package nowowiejski.michal.shopping

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val shoppingListNavigationRoute = "shopping_list_route/"

fun NavController.navigateToShoppingList(navOptions: NavOptions? = null) {
    this.navigate(shoppingListNavigationRoute, navOptions)
}

fun NavGraphBuilder.shoppingListScreen() {
    composable(
        route = shoppingListNavigationRoute,
    ) {
        ShoppingListScreen()
    }
}

@Composable
internal fun ShoppingListScreen(){
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "ShoppingListScreen")
    }
}