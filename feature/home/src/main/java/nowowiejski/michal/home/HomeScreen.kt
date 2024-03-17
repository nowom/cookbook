package nowowiejski.michal.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nowowiejski.michal.model.Recipe
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    onRecipeClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(uiState, onRecipeClick)
}

@Composable
fun HomeScreen(uiState: HomeUiState, onRecipeClick: () -> Unit) {
    when (uiState) {
        HomeUiState.Error -> {}
        HomeUiState.Loading -> {}
        is HomeUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.padding(24.dp),
                ) {
                    items(items = uiState.recipes) {recipe->
                        RecipeListItem(recipe, onRecipeClick)
                    }
//                    uiState.recipes.forEach { recipe ->
//                        item {
//                            RecipeListItem(recipe, onRecipeClick)
//                        }
//                        item {
//                            Spacer(modifier = Modifier.height(8.dp))
//                        }
//                    }
                }
            }
        }
    }
}

@Composable
private fun HomeItem(recipe: Recipe, onRecipeClick: (String) -> Unit) {
    Box(
        Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {
                onRecipeClick.invoke("")
            }
    ) {
        Text(text = recipe.recipeName)
    }
}

@Composable
fun RecipeListItem(
    recipe: Recipe,
    onRecipeClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onRecipeClicked),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = recipe.recipeName, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = recipe.shortDescription)
        }
    }
}