package nowowiejski.michal.domain.usecase

import nowowiejski.michal.domain.RecipeRepository
import nowowiejski.michal.model.Recipe

class SaveRecipeUseCase(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke(recipe: Recipe) {
        recipeRepository.saveRecipe(recipe)
    }
}