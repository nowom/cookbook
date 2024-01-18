package nowowiejski.michal.data.di

import nowowiejski.michal.domain.RecipeRepository
import org.koin.dsl.module

object DataModule {
    fun get() = module {
        factory<RecipeRepository> { nowowiejski.michal.data.RecipeRepository(get()) }
    }
}