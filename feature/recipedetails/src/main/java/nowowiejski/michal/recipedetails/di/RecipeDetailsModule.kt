package nowowiejski.michal.recipedetails.di

import nowowiejski.michal.recipedetails.RecipeDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object RecipeDetailsModule {

    fun get() = module {
        viewModel {
            RecipeDetailsViewModel()
        }
    }
}