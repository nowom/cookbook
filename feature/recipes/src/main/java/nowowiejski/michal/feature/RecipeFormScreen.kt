package nowowiejski.michal.feature

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.StateFlow
import nowowiejski.michal.model.Step
import org.koin.androidx.compose.koinViewModel

data class RecipeFormData(
    val recipeName: String = "",
    val shortDescription: String = "",
    val portions: Int = 0,
    val imageUri: Uri? = null,
    val ingredients: List<String> = emptyList(),
    val source: String = "",
    val cookTime: String = ""
)

@Composable
fun RecipeFormRoute() {
    RecipeFormScreen() {

    }
}

@Composable
internal fun RecipeFormScreen(
    viewModel: RecipeFormViewModel = koinViewModel(),
    onRecipeSubmit: (RecipeFormData) -> Unit
) {

    val uiState: RecipeFormUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect: Effect? by viewModel.effect.collectAsStateWithLifecycle(null)

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.onImageSelected(uri)
            }
        }
    )
    val context = LocalContext.current
    LaunchedEffect(uiEffect) {
        when (uiEffect) {
            is Effect.NavigateToSettings-> {
                Toast.makeText(context, "Navigate to settings", Toast.LENGTH_SHORT).show()
            }
            is Effect.OpenDialogChooser -> {
                galleryLauncher.launch("image/*")
            }
            else -> {}
        }
    }

    var recipeFormData by remember { mutableStateOf(RecipeFormData()) }

    var showIngredientsDialog by remember { mutableStateOf(false) }
    var showStepsDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Image selection placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9)
                .background(Color.Gray)
                .clickable {
                    galleryLauncher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            // Display selected image or icon
            if (uiState.imageUrl != null) {
                AsyncImage(
                    model = uiState.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                )
            } else {
                Icon(Icons.Default.Add, contentDescription = "Add Photo")
            }
        }

        OutlinedTextField(
            value = uiState.recipeName,
            onValueChange = {
                viewModel.onRecipeNameInputChanged(it)
                recipeFormData = recipeFormData.copy(recipeName = it)
            },
            label = { Text("Recipe Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )

        OutlinedTextField(
            value = recipeFormData.shortDescription,
            onValueChange = {
                viewModel.onDescriptionInputChanged(it)
                recipeFormData = recipeFormData.copy(shortDescription = it)
            },
            label = { Text("Short Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )

        OutlinedTextField(
            value = recipeFormData.portions.toString(),
            onValueChange = {
                val portions = it.toIntOrNull() ?: 0
                recipeFormData = recipeFormData.copy(portions = portions)
            },
            label = { Text("Number of Portions") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // You can perform any additional actions on submit here
                    onRecipeSubmit(recipeFormData)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )

        // OutlinedTextField for the source field
        OutlinedTextField(
            value = recipeFormData.source,
            onValueChange = {
                recipeFormData = recipeFormData.copy(source = it)
                viewModel.onSourceInputChanged(it)
            },
            label = { Text("Source") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )

        // OutlinedTextField for the cook time field
        OutlinedTextField(
            value = recipeFormData.cookTime,
            onValueChange = {
                recipeFormData = recipeFormData.copy(cookTime = it)
                viewModel.onCookTimeInputChanged(it)
            },
            label = { Text("Cook Time") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )


// Clickable box to open the bottom dialog for ingredients
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .aspectRatio(16f / 9)
//                .background(Color.Gray)
//                .clickable {
//                    showIngredientsDialog = true
//                },
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(Icons.Rounded.ArrowDropDown, contentDescription = "Ingredients")
//        }
        // Wyświetl listę kroków przepisu

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { showStepsDialog = true }) {
                Text("Add Recipe Step")
            }
        }
        uiState.steps.takeIf { it.isNotEmpty() }?.forEachIndexed { index, step ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = step.description)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(modifier = Modifier.size(12.dp),
                    onClick = { /* Obsługa kliknięcia przycisku edycji */ }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
        Button(
            onClick = {
                // You can perform any additional actions on submit here
                onRecipeSubmit(recipeFormData)
                viewModel.onSaveClicked()
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp)
        ) {
            Text("Submit Recipe")
        }

        // Bottom dialog for ingredients selection
        if (showIngredientsDialog) {
            IngredientsDialog(
                onIngredientsSelected = { selectedIngredients ->
                    // Handle the selected ingredients
                    // For example, update the state, display them, etc.
                    //recipeFormData = recipeFormData.copy(ingredients = selectedIngredients)
                },
                onDismiss = { showIngredientsDialog = false }
            )
        }
        var newStepDescription by remember { mutableStateOf("") }

        if (showStepsDialog) {
            AddStepDialog(
                onAddStep = {
                    viewModel.addRecipeStep(Step(it))
                    showStepsDialog = false
                },
                onDismiss = { showStepsDialog = false },
                newStepDescription = newStepDescription,
                onDescriptionChange = { newStepDescription = it }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RecipeFormPreview() {
    RecipeFormScreen(onRecipeSubmit = {})
}