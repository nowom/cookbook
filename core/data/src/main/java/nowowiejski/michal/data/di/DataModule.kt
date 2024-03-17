package nowowiejski.michal.data.di

import nowowiejski.michal.data.TagRepositoryImpl
import nowowiejski.michal.domain.RecipeRepository
import nowowiejski.michal.domain.TagRepository
import org.koin.dsl.module

object DataModule {
    fun get() = module {
        factory<RecipeRepository> {
            nowowiejski.michal.data.RecipeRepository(
                recipeDao = get(),
                ingredientDao = get(),
                stepDao = get(),
                appDispatcher = get(),
            )
        }

        factory<TagRepository> {
            TagRepositoryImpl(
                tagDao = get()
            )
        }
    }
}