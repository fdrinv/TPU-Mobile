package com.fedorinov.tpumobile

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fedorinov.tpumobile.ui.start.auth.AuthorizationScreen
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // - Устанавливаем заставку Splash-экрана
        installSplashScreen()

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
                        AuthorizationScreen(signUp  = {})
                    }
                }
            }
        )
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}
