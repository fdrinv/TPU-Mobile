package com.fedorinov.tpumobile

import android.app.Application
import android.content.res.Resources
import com.fedorinov.tpumobile.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(appModule)
        }

        // - Получаем ресурсы приложения
        App.resources = resources
    }

    companion object {

        private var resources: Resources? = null
        fun getAppResources(): Resources? {
            return resources
        }
    }

}