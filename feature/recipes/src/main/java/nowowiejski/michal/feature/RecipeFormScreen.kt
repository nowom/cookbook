package nowowiejski.michal.feature

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import nowowiejski.michal.designsystem.component.TextInput
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipeFormRoute(
    onIngredientClick: () -> Unit,
    onRecipeSubmit: () -> Unit,
) {
    RecipeFormScreen(
        onRecipeSubmit = onRecipeSubmit,
        onIngredientClick = onIngredientClick
    )
}

@Composable
internal fun RecipeFormScreen(
    viewModel: RecipeFormViewModel = koinViewModel(),
    onRecipeSubmit: () -> Unit,
    onIngredientClick: () -> Unit,
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
            is Effect.NavigateToHome -> {
                Toast.makeText(context, "Navigate to settings", Toast.LENGTH_SHORT).show()
                onRecipeSubmit()
            }

            is Effect.OpenDialogChooser -> {
                galleryLauncher.launch("image/*")
            }

            else -> {}
        }
    }

    var showIngredientsDialog by remember { mutableStateOf(false) }

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
        TextInput(
            value = uiState.recipeName,
            onValueChange = {
                viewModel.onRecipeNameInputChanged(it)
            },
            textFieldText = "Recipe Name",
        )

        TextInput(
            value = uiState.shortDescription,
            onValueChange = {
                viewModel.onDescriptionInputChanged(it)
            },
            textFieldText = "Short Description",
        )

        var newTag by remember { mutableStateOf("") }

        TagSelection(
            tags = uiState.tags,
            newTag = newTag,
            onTagSelected = { selectedTag ->
                viewModel.onSelectedTag(selectedTag.first)
            },
            onNewTagChanged = { newTag = it },
            onNewTagSubmit = {
                if (newTag.isNotBlank()) {
                    //TODO handling new tag
                    viewModel.addNewTag(newTag)
                }
            }
        )
//        var portions by remember {
//            mutableStateOf(1)
//        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text("Number of Portions:")
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                modifier = Modifier
                    .background(Color.Transparent)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    if (uiState.servings > 1) {
                        viewModel.onNumberOfServingsDecrease()
                    }
                }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Minus")
                }
                Text(
                    text = uiState.servings.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                IconButton(onClick = {
                    viewModel.onNumberOfServingsIncrease()
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Plus")
                }
            }
        }

        TextInput(
            value = uiState.source,
            onValueChange = {
                viewModel.onSourceInputChanged(it)
            },
            textFieldText = "Source",
        )

        TextInput(
            value = uiState.cookTime,
            onValueChange = {
                viewModel.onCookTimeInputChanged(it)
            },
            textFieldText = "Cook Time",
        )

        uiState.steps.forEachIndexed { index, stepState ->
            StepInput(
                stepState = stepState,
                onStepChanged = { step ->
                    viewModel.changeStepDescription(index, step)
                    if (index == uiState.steps.lastIndex && step.isNotBlank()) {
                        viewModel.addStep()
                    }
                },
                onRemoveStep = {
                    viewModel.removeStep(stepState)
                }
            )
        }
//        val stepsState = remember { mutableStateListOf(StepDisplayable(id = 1, description = "")) }
//
//        stepsState.forEachIndexed { index, stepState ->
//            StepInput(
//                stepState = stepState,
//                onStepChanged = { step ->
//                    //stepsState[index] = stepState.copy(description = step.description)
//                    viewModel.changeStepDescription(step)
//                    // Dodanie nowego pustego pola, jeśli aktualny krok jest ostatni i niepusty
//                    if (index == uiState.steps.lastIndex && step.description.isNotBlank()) {
//                        //uiState.steps.add(StepDisplayable(id = stepState.id + 1, description = ""))
//                    }
//                },
//                onRemoveStep = {
//
//                }
//            )
//        }

//        uiState.steps.takeIf { it.isNotEmpty() }?.forEachIndexed { index, step ->
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.padding(vertical = 8.dp)
//            ) {
//                Text(text = step.description)
//                Spacer(modifier = Modifier.weight(1f))
//                IconButton(modifier = Modifier.size(12.dp),
//                    onClick = { /* Obsługa kliknięcia przycisku edycji */ }) {
//                    Icon(Icons.Default.Edit, contentDescription = "Edit")
//                }
//            }
//        }

        Button(
            onClick = { showIngredientsDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Dodaj składniki")
        }


        Button(
            onClick = {
                // You can perform any additional actions on submit here
                onRecipeSubmit()
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
//            IngredientsDialog(
//                onIngredientsSelected = { selectedIngredients ->
//                    // Handle the selected ingredients
//                    // For example, update the state, display them, etc.
//                    //recipeFormData = recipeFormData.copy(ingredients = selectedIngredients)
//                },
//                onDismiss = { showIngredientsDialog = false }
//            )
            onIngredientClick.invoke()
        }

    }
}

@Composable
private fun StepInput(
    stepState: StepDisplayable,
    onStepChanged: (String) -> Unit,
    onRemoveStep: () -> Unit
) {
    OutlinedTextField(
        value = stepState.description,
        onValueChange = onStepChanged,
        label = { Text("Step Description") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        trailingIcon = {
            if (stepState.description.isNotEmpty()) {
                IconButton(onClick = onRemoveStep) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Step")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RecipeFormPreview() {
    RecipeFormScreen(onRecipeSubmit = {},
        onIngredientClick = {})
}