package nowowiejski.michal.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import nowowiejski.michal.database.dao.RecipeDao
import nowowiejski.michal.database.model.asExternalModel
import nowowiejski.michal.domain.RecipeRepository
import nowowiejski.michal.model.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) : RecipeRepository {
    override fun getAllRecipes(): Flow<List<Recipe>> {
        return flow {
            recipeDao.getAllRecipes().map {
                it.map { recipeEntity ->
                    recipeEntity.asExternalModel()
                }
            }
        }
    }
}