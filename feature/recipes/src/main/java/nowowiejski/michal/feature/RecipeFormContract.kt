package nowowiejski.michal.feature

import android.net.Uri
import nowowiejski.michal.model.Step
import nowowiejski.michal.model.Tag

data class RecipeFormUiState(
    val recipeName: String = "",
    val shortDescription: String = "",
    val portions: Int = 0,
    val imageUrl: Uri? = null,
    val ingredients: List<String> = emptyList(),
    val source: String = "",
    val cookTime: String = "",
    val showIngredientsDialog: Boolean = false,
    val steps: List<Step> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val error: Boolean? = null,
)

internal sealed interface UiIntent {
    data class UpdateRecipeName(val name: String) : UiIntent
    data class UpdateShortDescription(val description: String) : UiIntent
    data class UpdatePortions(val portions: Int) : UiIntent
    object SaveData: UiIntent
    object SelectImageFromGallery: UiIntent
    data class ImageSelected(val imageUri: Uri): UiIntent
    data class UpdateSource(val source: String): UiIntent
    data class UpdateCookTime(val cookTime: String): UiIntent
    data class AddStep(val step: Step): UiIntent
    object GetData: UiIntent
    data class AddTag(val tag: String): UiIntent
    data class SelectTag(val tag: Tag): UiIntent

}

internal sealed class UiStateChange {
    data class Initial(val tags: List<Tag>) : UiStateChange()
    object Loading : UiStateChange()
    object Error : UiStateChange()
    data class ChangeRecipeName(val recipeName: String) : UiStateChange()
    data class ChangeDescriptionName(val description: String) : UiStateChange()
    data class ChangePortions(val portions: Int) : UiStateChange()

    object SaveEvent: UiStateChange()

    data class SelectImage(val uri: Uri): UiStateChange()
    data class ChangeSource(val source: String) : UiStateChange()
    data class ChangeCookTime(val cookTime: String) : UiStateChange()

    data class AddStep(val step: Step) : UiStateChange()
    operator fun invoke(oldState: RecipeFormUiState) = when (this) {
        Error -> oldState
        Loading -> oldState
        is SaveEvent -> oldState
        is ChangeRecipeName -> oldState.copy(recipeName = recipeName)
        is ChangeDescriptionName -> oldState.copy(shortDescription = description)
        is ChangePortions -> oldState.copy(portions = portions)
        is SelectImage -> oldState.copy(imageUrl = uri)
        is ChangeSource -> oldState.copy(source = source)
        is ChangeCookTime -> oldState.copy(cookTime = cookTime)
        is AddStep -> oldState.copy(steps = oldState.steps.plus(step))
        is Initial -> oldState.copy(tags = tags)
    }
}

internal sealed interface Effect {
    object NavigateToSettings: Effect
    object OpenDialogChooser: Effect
}