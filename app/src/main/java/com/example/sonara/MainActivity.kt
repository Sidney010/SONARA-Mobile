package com.example.sonara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sonara.core.navigation.AppNavigation
import com.example.sonara.core.theme.SonaraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SonaraTheme {
                AppNavigation()
            }
        }
    }
}
