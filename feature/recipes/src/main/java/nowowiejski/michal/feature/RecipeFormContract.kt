package nowowiejski.michal.feature

import android.net.Uri
import nowowiejski.michal.model.Step
import nowowiejski.michal.model.Tag
import kotlin.random.Random

data class RecipeFormUiState(
    val recipeName: String = "",
    val shortDescription: String = "",
    val servings: Int = 0,
    val imageUrl: Uri? = null,
    val ingredients: List<String> = emptyList(),
    val source: String = "",
    val cookTime: String = "",
    val showIngredientsDialog: Boolean = false,
    val steps: List<StepDisplayable> = listOf(StepDisplayable(id = 0, "")),
    val tags: List<Tag> = emptyList(),
    val error: Boolean? = null,
)

data class StepDisplayable(val id: Int, val description: String)

internal sealed interface UiIntent {
    data class UpdateRecipeName(val name: String) : UiIntent
    data class UpdateShortDescription(val description: String) : UiIntent
    object IncreaseServings : UiIntent
    object DecreaseServings : UiIntent
    object SaveData : UiIntent
    object SelectImageFromGallery : UiIntent
    data class ImageSelected(val imageUri: Uri) : UiIntent
    data class UpdateSource(val source: String) : UiIntent
    data class UpdateCookTime(val cookTime: String) : UiIntent
    object AddStep : UiIntent
    data class ChangeStep(val index: Int, val description: String) : UiIntent
    data class RemoveStep(val step: StepDisplayable) : UiIntent

    object GetData : UiIntent
    data class AddTag(val tag: String) : UiIntent
    data class SelectTag(val tag: Tag) : UiIntent

}

internal sealed class UiStateChange {
    data class Initial(val tags: List<Tag>) : UiStateChange()
    object Loading : UiStateChange()
    object Error : UiStateChange()
    data class ChangeRecipeName(val recipeName: String) : UiStateChange()
    data class ChangeDescriptionName(val description: String) : UiStateChange()
    data class ChangePortions(val portions: Int) : UiStateChange()

    object SaveEvent : UiStateChange()

    data class SelectImage(val uri: Uri) : UiStateChange()
    data class ChangeSource(val source: String) : UiStateChange()
    data class ChangeCookTime(val cookTime: String) : UiStateChange()

    object AddStep : UiStateChange()
    data class RemoveStep(val step: StepDisplayable) : UiStateChange()
    object AddTag : UiStateChange()
    data class UpdateStep(val index: Int, val description: String) : UiStateChange()
    object IncreaseServings: UiStateChange()
    object  DecreaseServings: UiStateChange()
    operator fun invoke(oldState: RecipeFormUiState) = when (this) {
        Error -> oldState
        Loading -> oldState
        is SaveEvent -> oldState
        is ChangeRecipeName -> oldState.copy(recipeName = recipeName)
        is ChangeDescriptionName -> oldState.copy(shortDescription = description)
        is ChangePortions -> oldState.copy(servings = portions)
        is SelectImage -> oldState.copy(imageUrl = uri)
        is ChangeSource -> oldState.copy(source = source)
        is ChangeCookTime -> oldState.copy(cookTime = cookTime)
        is AddStep -> {
            val newStep = StepDisplayable(id = (Random.nextInt() + oldState.steps.lastIndex.inc()), description = "")
            oldState.copy(steps = oldState.steps + newStep)
        }
        is UpdateStep -> oldState.copy(
            steps = oldState.steps.changeStep(index, description)
        )

        is RemoveStep -> oldState.copy(
            steps = oldState.steps.filterNot { it.id == step.id }
        )

        is Initial -> oldState.copy(tags = tags)
        is AddTag -> oldState
        is IncreaseServings -> oldState.copy(servings = oldState.servings.inc())
        is DecreaseServings -> oldState.copy(servings = oldState.servings.dec())
    }
}

internal sealed interface Effect {
    object NavigateToHome : Effect
    object OpenDialogChooser : Effect
}

private fun List<StepDisplayable>.changeStep(
    index: Int,
    description: String
): List<StepDisplayable> {
    val step = this[index].copy(description = description)
    if (index != -1) {
        return toMutableList().apply { set(index, step) }
    }
    return this
}

fun StepDisplayable.toDomain(): Step = Step(
    this.description
)