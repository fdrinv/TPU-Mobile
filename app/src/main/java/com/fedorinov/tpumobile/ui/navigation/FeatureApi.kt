package com.fedorinov.tpumobile.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FeatureApi {

    // - Входная точка в модуль
    fun startRoute(): String

    // - Регистрация маршрутов
    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    )

}