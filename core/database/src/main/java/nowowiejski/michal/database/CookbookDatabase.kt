package nowowiejski.michal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nowowiejski.michal.database.dao.IngredientDao
import nowowiejski.michal.database.dao.RecipeDao
import nowowiejski.michal.database.dao.StepDao
import nowowiejski.michal.database.dao.TagDao
import nowowiejski.michal.database.model.IngredientEntity
import nowowiejski.michal.database.model.RecipeEntity
import nowowiejski.michal.database.model.StepEntity
import nowowiejski.michal.database.model.TagEntity

@Database(
    entities = [RecipeEntity::class, IngredientEntity::class, StepEntity::class, TagEntity::class,],
    version = 1,
    exportSchema = false
)
abstract class CookbookDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun stepDao(): StepDao
    abstract fun tagDao(): TagDao
}