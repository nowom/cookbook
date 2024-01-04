package nowowiejski.michal.cookbook

import android.app.Application
import nowowiejski.michal.common.di.DispatchersModule
import nowowiejski.michal.home.di.HomeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                    DispatchersModule.get(),
                    HomeModule.get(),
                )
            )
        }
    }
}