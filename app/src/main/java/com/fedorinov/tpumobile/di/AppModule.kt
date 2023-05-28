package com.fedorinov.tpumobile.di

import com.fedorinov.tpumobile.data.database.RoomDb
import com.fedorinov.tpumobile.data.repositories.AuthRepository
import com.fedorinov.tpumobile.data.repositories.CommonRepository
import com.fedorinov.tpumobile.data.repositories.RegistrationRepository
import com.fedorinov.tpumobile.data.rest.RestApiTpu
import com.fedorinov.tpumobile.logic.service.LinkLoaderService
import com.fedorinov.tpumobile.logic.sync.Synchronize
import com.fedorinov.tpumobile.ui.menu.MenuViewModel
import com.fedorinov.tpumobile.ui.start.auth.AuthorizationViewModel
import com.fedorinov.tpumobile.ui.start.reg.RegistrationViewModel
import com.fedorinov.tpumobile.userPreferencesStore
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DataStoreUserPreferences = "DATASTORE_USER_PROTOBUF"

val appModule = module {

    // App Coroutine Scope
    single { CoroutineScope(SupervisorJob()) }

    // Database
    single { RoomDb.databaseBuilder(androidContext(), get()) }

    // Services
    factory { LinkLoaderService(get(), get()) }

    // Sync
    single { Synchronize(get(), get(), get(), get(), get()) }

    // Dao
    single { get<RoomDb>().groupDao() }
    single { get<RoomDb>().linkDao() }

    // Rest-Api
    single { RestApiTpu() }

    // DataStore
    single(named(DataStoreUserPreferences)) { androidContext().userPreferencesStore }

    // Repositories
    single { AuthRepository(get(), get(named(DataStoreUserPreferences))) }
    single { CommonRepository(get()) }
    single { RegistrationRepository(get()) }

    // ViewModels
    viewModel { AuthorizationViewModel(get()) }
    viewModel { RegistrationViewModel(get(), get()) }
    viewModel { MenuViewModel(get(), get(), get(), get()) }

}