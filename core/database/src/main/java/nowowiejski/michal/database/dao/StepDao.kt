package nowowiejski.michal.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nowowiejski.michal.database.model.StepEntity

@Dao
interface StepDao {
    @Insert
    suspend fun insertStep(step: StepEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(steps: List<StepEntity>)
    @Query("SELECT * FROM steps WHERE recipeId = :recipeId")
    fun getStepsForRecipe(recipeId: Long): Flow<List<StepEntity>>
}