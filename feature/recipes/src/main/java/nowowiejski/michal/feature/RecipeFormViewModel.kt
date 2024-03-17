package nowowiejski.michal.feature

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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
import nowowiejski.michal.model.Ingredient
import nowowiejski.michal.model.Recipe
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
                    UiStateChange.AddStep
                )
            }

            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val addTagFlow = filterIsInstance<UiIntent.AddTag>()
            .flatMapLatest {
                flowOf(
                    UiStateChange.AddTag
                )
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val changeStepFlow = filterIsInstance<UiIntent.ChangeStep>()
            .flatMapLatest {
                flowOf(
                    UiStateChange.UpdateStep(it.index, it.description)
                )
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val cookbookFlow = filterIsInstance<UiIntent.UpdateCookTime>()
            .map { intent->
                UiStateChange.ChangeCookTime(intent.cookTime)
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

        val removeStepFlow = filterIsInstance<UiIntent.RemoveStep>()
            .map { intent ->
                UiStateChange.RemoveStep(intent.step)
            }
            .distinctUntilChanged()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val increasePortionFlow = filterIsInstance<UiIntent.IncreaseServings>()
            .map {
                UiStateChange.IncreaseServings
            }
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        val decreasePortionFlow = filterIsInstance<UiIntent.DecreaseServings>()
            .map {
                UiStateChange.DecreaseServings
            }
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        return merge(
            recipeNameChangeFlow,
            shortDescriptionChangeFlow,
            saveDataFlow,
            selectImageFlow,
            imageSelectedFlow,
            updateSourceFlow,
            addStepFlow,
            initFlow,
            addTagFlow,
            changeStepFlow,
            removeStepFlow,
            increasePortionFlow,
            decreasePortionFlow,
            cookbookFlow,
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

    fun changeStepDescription(index: Int, stepDisplayable: String) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.ChangeStep(index, stepDisplayable))
        }
    }

    fun addStep() {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.AddStep)
        }
    }

    fun removeStep(stepDisplayable: StepDisplayable) {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.RemoveStep(stepDisplayable))
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

    fun onNumberOfServingsChanged(servings: Int) {
        viewModelScope.launch {
            //intentFlow.emit(UiIntent.UpdateServings(servings))
        }
    }

    fun onNumberOfServingsIncrease() {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.IncreaseServings)
        }
    }

    fun onNumberOfServingsDecrease() {
        viewModelScope.launch {
            intentFlow.emit(UiIntent.DecreaseServings)
        }
    }

    private val _ingredients = MutableStateFlow(listOf(""))
    val ingredients: StateFlow<List<String>> get() = _ingredients

    fun addEmptyIngredient() {
        val currentList = _ingredients.value.toMutableList()
        currentList.add("")
        _ingredients.value = currentList
    }

    fun removeIngredient(index: Int) {
        val currentList = _ingredients.value.toMutableList()
        if (index in currentList.indices) {
            currentList.removeAt(index)
            _ingredients.value = currentList
        }
    }

    fun updateIngredient(index: Int, newName: String) {
        val currentList = _ingredients.value.toMutableList()
        if (index in currentList.indices) {
            currentList[index] = newName
            _ingredients.value = currentList
        }
    }

    private fun save(): Flow<UiStateChange> {
        return completableFlow {
            val value = uiState.value
            saveRecipeUseCase(
                recipe = Recipe(
                    recipeName = value.recipeName,
                    shortDescription = value.shortDescription,
                    source = value.recipeName,
                    cookTime = value.cookTime,
                    portions = value.servings,
                    ingredients = ingredients.value.map {
                              Ingredient(it, "10g")
                    },
                    steps = value.steps.map {
                        it.toDomain()
                    },
                )
            )
        }.map {
            UiStateChange.SaveEvent
        }.onEach {
            _effect.send(Effect.NavigateToHome)
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