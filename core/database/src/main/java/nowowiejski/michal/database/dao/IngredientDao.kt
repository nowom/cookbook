package nowowiejski.michal.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nowowiejski.michal.database.model.IngredientEntity

@Dao
interface IngredientDao {
    @Insert
    suspend fun insertIngredient(ingredient: IngredientEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ingredients: List<IngredientEntity>)
    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    fun getIngredientsForRecipe(recipeId: Long): Flow<List<IngredientEntity>>
}