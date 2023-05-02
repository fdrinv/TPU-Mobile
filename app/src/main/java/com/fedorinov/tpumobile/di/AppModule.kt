package com.fedorinov.tpumobile.di

import com.fedorinov.tpumobile.data.rest.RestApiTpu
import com.fedorinov.tpumobile.ui.start.auth.AuthorizationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // App Coroutine Scope
    single { CoroutineScope(SupervisorJob()) }

    // ViewModels
    viewModel { AuthorizationViewModel() }

    // Rest-Api
    single { RestApiTpu() }
}