package nowowiejski.michal.domain

import kotlinx.coroutines.flow.Flow
import nowowiejski.michal.model.Recipe

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
}