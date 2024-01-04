package nowowiejski.michal.common.di

import nowowiejski.michal.common.AppDispatcher
import nowowiejski.michal.common.AppDispatcherImpl
import org.koin.dsl.module

object DispatchersModule {
    fun get() = module {
        single<AppDispatcher> { AppDispatcherImpl() }
    }
}