package nowowiejski.michal.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import nowowiejski.michal.database.model.RecipeEntity

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreRecipes(recipes: List<RecipeEntity>): List<Long>
}