package com.example.sonara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.sonara.features.inicial.ui.StartBemVindoScreen
import com.example.sonara.ui.theme.SONARATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SONARATheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StartBemVindoScreen()
                }
            }
        }
    }
}
