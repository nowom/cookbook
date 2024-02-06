package nowowiejski.michal.database.di

import android.content.Context
import androidx.room.Room
import nowowiejski.michal.database.CookbookDatabase
import nowowiejski.michal.database.dao.IngredientDao
import nowowiejski.michal.database.dao.RecipeDao
import nowowiejski.michal.database.dao.StepDao
import nowowiejski.michal.database.dao.TagDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DatabaseModule {
    fun get() = module {
        single<CookbookDatabase> {
            provideDB(androidContext())
        }

        single<RecipeDao> {
            provideRecipeDao(get())
        }
        single<IngredientDao> {
            provideIngredientDao(get())
        }
        single<StepDao> {
            provideStepDao(get())
        }
        single<TagDao> {
            provideTagDao(get())
        }
    }

}

fun provideDB(context: Context) =
    Room.databaseBuilder(
        context,
        CookbookDatabase::class.java,
        "cookbook-database",
    ).createFromAsset("database/cookbook.db")
        .fallbackToDestructiveMigration()
        .build()

fun provideRecipeDao(db: CookbookDatabase) = db.recipeDao()
fun provideIngredientDao(db: CookbookDatabase) = db.ingredientDao()
fun provideStepDao(db: CookbookDatabase) = db.stepDao()
fun provideTagDao(db: CookbookDatabase) = db.tagDao()