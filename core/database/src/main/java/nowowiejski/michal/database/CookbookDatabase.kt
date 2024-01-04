package nowowiejski.michal.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import nowowiejski.michal.database.dao.RecipeDao
import nowowiejski.michal.database.model.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)

abstract class CookbookDatabase : RoomDatabase() {
    abstract fun awardPositionDao(): RecipeDao
}