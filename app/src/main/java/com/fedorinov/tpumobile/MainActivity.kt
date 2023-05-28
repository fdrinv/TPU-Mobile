package com.fedorinov.tpumobile

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fedorinov.tpumobile.ui.NavGraphs
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // - Устанавливаем заставку Splash-экрана
        installSplashScreen()
        /*runBlocking {
            val restApi: RestApiTpu by inject(RestApiTpu::class.java)
            val result = restApi.api.getLinks(
                token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlYWYyNUB0cHUucnUiLCJleHAiOjE2ODU1NTI0MDAsInNhbHQiOiJkQWpESlRSaHVuaiJ9.RD0LOhQIzSIwQYj30O_7KP2xZpG94Pgl-EvfmcG6TvgVTuXWJj6EwnNQ8gUU6TFjV0CU_4O_dYFKzw5LtEu5EQ",
                language = "ru",
                languageId = "E06ED586-DD3B-4751-9BED-764047793AFA",
                email = "eaf25@tpu.ru"
            )
            val bfs: BreathFirstSearch<LinkResponse> = BreathFirstSearch(Vertex())
            Log.i(TAG, "result body = ${result.body()}")
        }*/
        super.onCreate(savedInstanceState)

        // - Инициализируем Compose
        initialCompose(this)
    }

    /**
     * Инициализирует Compose в приложение.
     */
    private fun initialCompose(context: Context) {
        setContentView(
            ComposeView(context).apply {
                setContent {
                    TPUMobileTheme {
                        // Получаем системный бар (aka StatusBar)
                        val systemUiController = rememberSystemUiController()
                        // На каждую удачную рекомпозицию меняем цвет системного бара
                        SideEffect {
                            systemUiController.setStatusBarColor(
                                color = Color.White,
                                darkIcons = true
                            )
                        }
                        // Вызываем стартовый экран приложения
                        DestinationsNavHost(navGraph = NavGraphs.root)
                    }
                }
            }
        )
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}
