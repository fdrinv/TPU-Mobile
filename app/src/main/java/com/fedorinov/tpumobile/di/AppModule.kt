package com.fedorinov.tpumobile.di

import com.fedorinov.tpumobile.ui.start.StartFeature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val appModule = module {

    // App Coroutine Scope
    single { CoroutineScope(SupervisorJob()) }

    // Navigation Features
    single { StartFeature() }

}