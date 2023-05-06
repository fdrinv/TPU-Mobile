package com.fedorinov.tpumobile.di

import android.net.ConnectivityManager
import com.fedorinov.tpumobile.data.repositories.AuthRepository
import com.fedorinov.tpumobile.data.rest.RestApiTpu
import com.fedorinov.tpumobile.ui.start.auth.AuthorizationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // App Coroutine Scope
    single { CoroutineScope(SupervisorJob()) }

    // ViewModels
    viewModel { AuthorizationViewModel(get()) }

    // Rest-Api
    single { RestApiTpu() }

    // Repositories
    single { AuthRepository(get(), androidContext().getSystemService(ConnectivityManager::class.java) as ConnectivityManager) }


}