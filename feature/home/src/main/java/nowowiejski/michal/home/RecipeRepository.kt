package nowowiejski.michal.home

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import nowowiejski.michal.common.AppDispatcher
import nowowiejski.michal.model.Recipe

interface RecipeRepository {
    fun getRecipes(): Flow<List<Recipe>>
}

class FakeRecipeRepository(private val ioDispatcher: AppDispatcher) : RecipeRepository {

    override fun getRecipes(): Flow<List<Recipe>> {
        return flow {
            emit(
                generateTestRecipeList()
            )
        }.flowOn((ioDispatcher.io))
    }
}

private fun generateTestRecipeList(): List<Recipe> {
    return listOf(
        Recipe(
            recipeName = "Spaghetti Bolognese",
            shortDescription = "Classic Italian pasta dish",
            portions = 4,
            ingredients = listOf("Spaghetti", "Ground beef", "Tomato sauce", "Onion", "Garlic"),
            source = "Italian Kitchen",
            cookTime = "30 minutes"
        ),
        Recipe(
            recipeName = "Chicken Caesar Salad",
            shortDescription = "Healthy and delicious salad",
            portions = 2,
            ingredients = listOf(
                "Chicken breast",
                "Romaine lettuce",
                "Caesar dressing",
                "Croutons"
            ),
            source = "Fit Food Magazine",
            cookTime = "20 minutes"
        ),
    )
}