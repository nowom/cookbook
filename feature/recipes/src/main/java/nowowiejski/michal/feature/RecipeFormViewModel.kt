package nowowiejski.michal.feature

import android.net.Uri
import android.util.Log
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nowowiejski.michal.domain.usecase.GetTagsUseCase
import nowowiejski.michal.domain.usecase.SaveRecipeUseCase
import nowowiejski.michal.model.Recipe
import nowowiejski.michal.model.Step
import nowowiejski.michal.model.Tag

@OptIn(ExperimentalCoroutinesApi::class)
internal class RecipeFormViewModel(
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val getTagsUseCase: GetTagsUseCase,
) : ViewModel() {

    private val intentFlow = MutableSharedFlow<UiIntent>()
    val uiState: StateFlow<RecipeFormUiState> = intentFlow
        .onStart { emit(UiIntent.GetData) }
        .toStateChangeFlow()
        .scan(RecipeFormUiState()) { state, change -> change(state) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, RecipeFormUiState())

    private val _effect: Channel<Effect> = Channel()
    internal val effect: Flow<Effect> = _effect.receiveAsFlow()

    private fun Flow<UiIntent>.toStateChangeFlow(): Flow<UiStateChange> {
        val initFlow = filterIsInstance<UiIntent.GetData>()
            .flatMapLatest { loadTags() }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
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
                selectedImageFromGallery()
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val imageSelectedFlow = filterIsInstance<UiIntent.ImageSelected>()
            .flatMapLatest {
                flowOf(
                    UiStateChange.SelectImage(
                        it.imageUri
                    )
                )
            }.distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val updateSourceFlow = filterIsInstance<UiIntent.UpdateSource>()
            .flatMapLatest {
                flowOf(
                    UiStateChange.ChangeSource(
                        it.source
                    )
                )
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val addStepFlow = filterIsInstance<UiIntent.AddStep>()
            .flatMapLatest {
                flowOf(
                    UiStateChange.AddStep(
                        it.step
                    )
                )
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

        return merge(
            recipeNameChangeFlow,
            shortDescriptionChangeFlow,
            portionsChangeFlow,
            saveDataFlow,
            selectImageFlow,
            imageSelectedFlow,
            updateSourceFlow,
            addStepFlow,
            initFlow,
        )
    }

    private fun loadTags(): Flow<UiStateChange> {
        return getTagsUseCase().map {
            UiStateChange.Initial(
                it
            )
        }
    }

    fun onRecipeNameInputChanged(recipeName: String) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.UpdateRecipeName(recipeName))
        }
    }

    fun onDescriptionInputChanged(description: String) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.UpdateShortDescription(description))
        }
    }

    fun onSourceInputChanged(source: String) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.UpdateSource(source))
        }
    }

    fun onCookTimeInputChanged(cookTime: String) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.UpdateCookTime(cookTime))
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

    fun onImageSelected(uri: Uri) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.ImageSelected(uri))
        }
    }

    private fun selectedImageFromGallery() = flow<UiStateChange> {
        _effect.send(Effect.OpenDialogChooser)
    }

    fun addRecipeStep(step: Step) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.AddStep(step))
        }
    }

    fun addNewTag(tag: String) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.AddTag(tag))
        }
    }

    fun onSelectedTag(tag: Tag) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.SelectTag(tag))
        }
    }

    private fun save(): Flow<UiStateChange> {
        return completableFlow {
            val value = uiState.value
            saveRecipeUseCase(
                recipe = Recipe(
                    recipeName = value.recipeName,
                    ingredients = listOf(),
                    steps = listOf()
                )
            )
            Log.d("asdf", uiState.value.steps.toString())
        }.map {
            UiStateChange.SaveEvent
        }.onEach {
            _effect.send(Effect.NavigateToSettings)
        }
    }
}

fun completableFlow(block: suspend () -> Unit): Flow<Result<Unit>> = flow {
    try {
        block()
        emit(Result.success(Unit))
    } catch (e: Exception) {
        emit(Result.failure(e))
    }
}