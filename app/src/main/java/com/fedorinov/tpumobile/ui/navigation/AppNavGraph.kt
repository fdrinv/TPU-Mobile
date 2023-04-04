package com.fedorinov.tpumobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.fedorinov.tpumobile.ui.start.StartFeature
import org.koin.androidx.compose.get

@Composable
fun AppNavGraph(
    startDestination: String,
    navController: NavHostController
) {

    // Стартовая фича
    val startFeature: StartFeature = get()

    // Регистрация фич
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // - Стартовая фича
        register(
            featureApi = startFeature,
            navController = navController
        )

    }

}