package nowowiejski.michal.recipedetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipeDetailsRoute(
    viewModel: RecipeDetailsViewModel = koinViewModel(),
) {
    RecipeDetailsScreen()
}

@Composable
fun RecipeDetailsScreen() {
    Text("Test")
}