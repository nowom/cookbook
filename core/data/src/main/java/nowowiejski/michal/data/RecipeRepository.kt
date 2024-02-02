package nowowiejski.michal.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nowowiejski.michal.database.dao.IngredientDao
import nowowiejski.michal.database.dao.RecipeDao
import nowowiejski.michal.database.dao.StepDao
import nowowiejski.michal.database.model.asEntity
import nowowiejski.michal.database.model.asExternalModel
import nowowiejski.michal.domain.RecipeRepository
import nowowiejski.michal.model.Recipe

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao,
    private val stepDao: StepDao
) :
    RecipeRepository {
    override fun getAllRecipes(): Flow<List<Recipe>> =
        recipeDao.getAllRecipes().map { populatedRecipe ->
            populatedRecipe.map {
                it.asExternalModel()
            }
        }

    override suspend fun saveRecipe(recipe: Recipe) {
        val recipeEntity = recipe.asEntity()
        recipeDao.insertOrIgnoreRecipe(recipeEntity)
        val ingredientEntities = recipe.ingredients.map {
            it.asEntity(recipeEntity.id)
        }
        val steps = recipe.steps.map {
            it.asEntity(recipeEntity.id)
        }
        ingredientDao.insertAll(ingredientEntities)
        stepDao.insertAll(steps)
    }
}