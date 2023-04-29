package com.fedorinov.tpumobile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // - Устанавливаем заставку Splash-экрана
        installSplashScreen()
        super.onCreate(savedInstanceState)
        Thread.sleep(2000L)
        Log.i(TAG, "Compose is initialed!")
        // - Инициализируем Compose
        setContent {
            TPUMobileTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Text("Compose")
                }
                // FIXME: Нужно создать первый стартовый экран, чтобы не вылетала ошибка регистрации маршрута.
                /*AppNavGraph(
                    startDestination = "",
                    navController = rememberNavController()
                )*/
            }
        }
    }

    /**
     * Инициализирует Compose в приложение.
     */
    private fun initialCompose(context: Context) {
        setContentView(
            ComposeView(context).apply {
                setContent {
                    TPUMobileTheme {
                        Surface(color = MaterialTheme.colorScheme.background) {
                            Text("Compose")
                        }
                        // FIXME: Нужно создать первый стартовый экран, чтобы не вылетала ошибка регистрации маршрута.
                        /*AppNavGraph(
                            startDestination = "",
                            navController = rememberNavController()
                        )*/
                    }
                }
            }
        )
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}
