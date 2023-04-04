package com.fedorinov.tpumobile.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

fun NavGraphBuilder.register(
    featureApi: FeatureApi,
    navController: NavHostController
) {
    featureApi.registerGraph(
        navGraphBuilder = this,
        navController = navController
    )
}