package com.fedorinov.tpumobile

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fedorinov.tpumobile.logic.sync.Synchronize
import com.fedorinov.tpumobile.ui.NavGraphs
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.inject
import org.koin.java.KoinJavaComponent.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // - Устанавливаем заставку Splash-экрана
        installSplashScreen()
        /*runBlocking {
            val synchronize: Synchronize by inject<Synchronize>(Synchronize::class.java)
            synchronize.doSync()
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
