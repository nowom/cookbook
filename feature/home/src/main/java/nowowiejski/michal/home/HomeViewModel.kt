package nowowiejski.michal.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import nowowiejski.michal.model.Recipe

class HomeViewModel(recipeRepository: RecipeRepository) : ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        recipeRepository.getRecipes().map(
            HomeUiState::Success,
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading,
        )
}

sealed interface HomeUiState {
    data class Success(val recipes: List<Recipe>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}