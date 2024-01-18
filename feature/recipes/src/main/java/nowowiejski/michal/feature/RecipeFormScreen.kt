package nowowiejski.michal.feature

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    recipeFormViewModel: RecipeFormViewModel = koinViewModel(),
    onRecipeSubmit: (RecipeFormData) -> Unit
) {

    val uiState: RecipeFormUiState by recipeFormViewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect: Effect? by recipeFormViewModel.effect.collectAsState(
        initial = null
    )
    when (uiEffect) {
        is Effect.NavigateToSettings -> {
            Toast.makeText(LocalContext.current, "text", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }

    var recipeFormData by remember { mutableStateOf(RecipeFormData()) }

    // Image selection state
    var imageSelectionEnabled by remember { mutableStateOf(false) }

    // Image URI
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var showIngredientsDialog by remember { mutableStateOf(false) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
            }
        }
    )
    // ActivityResultLauncher for image selection
//    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        uri?.let {
//            imageUri = it
//            recipeFormData = recipeFormData.copy(imageUri = it)
//        }
//    }

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
                    imageSelectionEnabled = true
                    recipeFormViewModel.onSelectImageClicked()
                    galleryLauncher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            // Display selected image or icon
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri, // Replace with actual image loading logic
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                )
            } else {
                Icon(Icons.Default.Add, contentDescription = "Add Photo")
            }

            // Image selection icon
            if (imageSelectionEnabled) {
//                Icon(
//                    contentDescription = "Add Photo",
//                    modifier = Modifier
//                        .size(48.dp)
//                        .background(Color.White, MaterialTheme.shapes.large)
//                        .clip(MaterialTheme.shapes.large)
//                        .clickable {
//                            //getContent.launch("image/*")
//                        }
//                )
            }
        }

        OutlinedTextField(
            value = recipeFormData.recipeName,
            onValueChange = {
                recipeFormViewModel.onRecipeNameInputChanged(it)
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
                recipeFormViewModel.onDescriptionInputChanged(it)
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
            onValueChange = { recipeFormData = recipeFormData.copy(source = it) },
            label = { Text("Source") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )

        // OutlinedTextField for the cook time field
        OutlinedTextField(
            value = recipeFormData.cookTime,
            onValueChange = { recipeFormData = recipeFormData.copy(cookTime = it) },
            label = { Text("Cook Time") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )


// Clickable box to open the bottom dialog for ingredients
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9)
                .background(Color.Gray)
                .clickable {
                    showIngredientsDialog = true
                },
            contentAlignment = Alignment.Center
        ) {
            // Display icon or other content as needed
            Icon(Icons.Rounded.ArrowDropDown, contentDescription = "Ingredients")
        }

        Button(
            onClick = {
                // You can perform any additional actions on submit here
                onRecipeSubmit(recipeFormData)
                recipeFormViewModel.onSaveClicked()
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
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeFormPreview() {
    RecipeFormScreen(onRecipeSubmit = {})
}