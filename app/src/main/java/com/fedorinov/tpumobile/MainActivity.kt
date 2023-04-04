package com.fedorinov.tpumobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.fedorinov.tpumobile.ui.navigation.AppNavGraph
import com.fedorinov.tpumobile.ui.theme.TPUMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TPUMobileTheme {
                // FIXME: Нужно создать первый стартовый экран, чтобы не вылетала ошибка регистрации маршрута.
                AppNavGraph(
                    startDestination = "",
                    navController = rememberNavController()
                )
            }
        }
    }
}