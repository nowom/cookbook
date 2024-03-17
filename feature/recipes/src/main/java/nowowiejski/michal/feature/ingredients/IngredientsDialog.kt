package nowowiejski.michal.feature.ingredients

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nowowiejski.michal.designsystem.component.TextInput
import nowowiejski.michal.feature.RecipeFormViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddIngredientsScreen(
    viewModel: RecipeFormViewModel = koinViewModel(),
    onNavigateUp: () -> Unit
) {
    val ingredientsState by viewModel.ingredients.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Dodaj składniki",
                        fontSize = 20.sp,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            ingredientsState.forEachIndexed { index, ingredient ->
                IngredientInput(
                    ingredient = ingredient,
                    onIngredientChanged = { newIngredient ->
                        viewModel.updateIngredient(index, newIngredient)
                    },
                    onRemoveIngredient = {
                        viewModel.removeIngredient(index)
                    }
                )
            }

            Button(
                onClick = { viewModel.addEmptyIngredient() },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Dodaj nowy składnik")
            }

            Button(
                onClick = {
                    onNavigateUp()
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Zapisz")
            }
        }
    }
}

@Composable
fun IngredientInput(
    ingredient: String,
    onIngredientChanged: (String) -> Unit,
    onRemoveIngredient: () -> Unit
) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextInput(
            value = ingredient,
            onValueChange = onIngredientChanged,
            textFieldText = "Składnik",
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onRemoveIngredient) {
            Icon(Icons.Default.Delete, contentDescription = "Usuń składnik")
        }
    }
}