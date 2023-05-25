package com.fedorinov.tpumobile

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.fedorinov.tpumobile.data.datastore.UserPreferencesSerializer
import com.fedorinov.tpumobile.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

private const val USER_PREFERENCES = "user_preferences.pb"

val Context.userPreferencesStore: DataStore<UserPreferences> by dataStore(
    fileName = USER_PREFERENCES,
    serializer = UserPreferencesSerializer
)

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