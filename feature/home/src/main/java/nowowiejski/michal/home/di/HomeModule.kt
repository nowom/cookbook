package nowowiejski.michal.home.di

import nowowiejski.michal.home.FakeRecipeRepository
import nowowiejski.michal.home.HomeViewModel
import nowowiejski.michal.home.RecipeRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object HomeModule {
    fun get() = module {
        single<RecipeRepository> { FakeRecipeRepository(get()) }
        viewModel {
            HomeViewModel(get())
        }
    }
}