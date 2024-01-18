package nowowiejski.michal.feature.di

import nowowiejski.michal.feature.RecipeFormViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object RecipeFormModule {
    fun get() = module {
        viewModel {
            RecipeFormViewModel()
        }
    }
}