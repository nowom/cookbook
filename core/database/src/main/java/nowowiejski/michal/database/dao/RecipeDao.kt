package nowowiejski.michal.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nowowiejski.michal.database.model.RecipeEntity

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreRecipes(recipes: List<RecipeEntity>): List<Long>
}
