package nowowiejski.michal.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
internal class RecipeFormViewModel : ViewModel() {

    private val intentFlow = MutableSharedFlow<UiIntent>()
    val uiState: StateFlow<RecipeFormUiState> = intentFlow
        .toStateChangeFlow()
        .scan(RecipeFormUiState()) { state, change -> change(state) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, RecipeFormUiState())

    private val _effect: Channel<Effect> = Channel()
    internal val effect: Flow<Effect> = _effect.receiveAsFlow()

    private fun Flow<UiIntent>.toStateChangeFlow(): Flow<UiStateChange> {
        val recipeNameChangeFlow = filterIsInstance<UiIntent.UpdateRecipeName>()
            .flatMapLatest {
                flowOf(
                    UiStateChange.ChangeRecipeName(
                        it.name
                    )
                )
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val shortDescriptionChangeFlow = filterIsInstance<UiIntent.UpdateShortDescription>()
            .flatMapLatest {
                flowOf(
                    UiStateChange.ChangeDescriptionName(
                        it.description
                    )
                )
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val portionsChangeFlow = filterIsInstance<UiIntent.UpdatePortions>()
            .flatMapLatest {
                flowOf(
                    UiStateChange.ChangePortions(
                        it.portions
                    )
                )
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val saveDataFlow = filterIsInstance<UiIntent.SaveData>()
            .flatMapLatest {
                save()
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val selectImageFlow = filterIsInstance<UiIntent.SelectImageFromGallery>()
            .flatMapLatest {
                flowOf(
                    UiStateChange.ChangePortions(
                        it.portions
                    )
                ).onEach {

                }
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        return merge(
            recipeNameChangeFlow,
            shortDescriptionChangeFlow,
            portionsChangeFlow,
            saveDataFlow,
            selectImageFlow,
        )
    }

    fun onRecipeNameInputChanged(recipeName: String) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.UpdateRecipeName(recipeName))
        }
    }

    fun onDescriptionInputChanged(recipeName: String) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.UpdateRecipeName(recipeName))
        }
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.SaveData)
        }
    }

    fun onSelectImageClicked() {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.SelectImageFromGallery)
        }
    }

    private fun save(): Flow<UiStateChange> {
        return flowOf(
            UiStateChange.SaveEvent
        ).onEach {
            _effect.send(Effect.NavigateToSettings)
        }
    }
}