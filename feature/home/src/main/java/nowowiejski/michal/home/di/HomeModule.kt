package nowowiejski.michal.home.di

import nowowiejski.michal.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object HomeModule {
    fun get() = module {
        viewModel {
            HomeViewModel(get())
        }
    }
}