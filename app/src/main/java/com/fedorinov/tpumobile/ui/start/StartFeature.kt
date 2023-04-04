package com.fedorinov.tpumobile.ui.start

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.fedorinov.tpumobile.ui.navigation.FeatureApi

class StartFeature : FeatureApi {

    // -= [Экран авторизации] =-
    private val routePathForAuth = "start/auth"
    fun routeToAuth() = routePathForAuth

    // Входная точка в фичу по умолчанию
    override fun startRoute() = routeToAuth()

    // Регистрация маршрутов (экранов фичи)
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        // TODO: Экран авторизации, меню.
    }

    companion object {
        private val TAG = StartFeature::class.simpleName
    }

}