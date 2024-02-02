package nowowiejski.michal.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import nowowiejski.michal.database.model.RecipeEntity
import nowowiejski.michal.database.model.PopulatedRecipe

@Dao
interface RecipeDao {

    @Query("SELECT id FROM recipes")
    fun getAllRecipeIds(): Flow<List<Long>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreRecipes(recipes: List<RecipeEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreRecipe(recipeEntity: RecipeEntity)

//    @Query("SELECT * FROM recipes JOIN ingredients ON recipes.id = ingredients.recipeId")
//    fun getAllRecipes(): Map<RecipeEntity, List<IngredientEntity>>

    @Transaction
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<PopulatedRecipe>>
}
//https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1