package nowowiejski.michal.domain.usecase

import nowowiejski.michal.domain.RecipeRepository
import nowowiejski.michal.model.Recipe

class GetSavedRecipesUseCase(private val recipeRepository: RecipeRepository) {
    suspend fun invoke(recipe: Recipe) {
        recipeRepository.saveRecipe(recipe)
    }
}