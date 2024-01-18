package nowowiejski.michal.feature

data class RecipeFormUiState(
    val recipeName: String = "",
    val shortDescription: String = "",
    val portions: Int = 0,
    val imageUrl: String = "",
    val ingredients: List<String> = emptyList(),
    val source: String = "",
    val cookTime: String = "",
    val showIngredientsDialog: Boolean = false
)

internal sealed interface UiIntent {
    data class UpdateRecipeName(val name: String) : UiIntent
    data class UpdateShortDescription(val description: String) : UiIntent
    data class UpdatePortions(val portions: Int) : UiIntent
    object SaveData: UiIntent

    object SelectImageFromGallery: UiIntent
}

internal sealed class UiStateChange {
    object Loading : UiStateChange()
    object Error : UiStateChange()
    data class ChangeRecipeName(val recipeName: String) : UiStateChange()
    data class ChangeDescriptionName(val description: String) : UiStateChange()
    data class ChangePortions(val portions: Int) : UiStateChange()

    object SaveEvent: UiStateChange()


    operator fun invoke(oldState: RecipeFormUiState) = when (this) {
        Error -> oldState
        Loading -> oldState
        is SaveEvent -> oldState
        is ChangeRecipeName -> oldState.copy(recipeName = recipeName)
        is ChangeDescriptionName -> oldState.copy(shortDescription = description)
        is ChangePortions -> oldState.copy(portions = portions)
//        is SaveEvent -> oldState.copy(userInfo = userInfo)
    }
}

internal sealed interface Effect {
    object NavigateToSettings: Effect
}