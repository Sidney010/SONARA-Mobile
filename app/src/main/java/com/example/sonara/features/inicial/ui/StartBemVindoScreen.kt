package com.example.sonara.features.inicial.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sonara.R
import com.example.sonara.core.theme.LocalGradients
import com.example.sonara.features.inicial.components.ButtonEntar
import com.example.sonara.features.inicial.components.WelcomeHeader

@Composable
fun StartBemVindoScreen(modifier: Modifier = Modifier) {
    val gradients = LocalGradients.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradients.primaryBackground),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp,36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {

            Image(
                painter = painterResource(R.drawable.sonara_logo),
                contentDescription = "Icone da Sonara",
            )

            WelcomeHeader(
                title = "BEM-VINDO A SONARA",
                subtitle = "UNIFICANDO IDEIAS, REALIZANDO SONHOS"
            )

            Spacer(modifier = Modifier.height(32.dp))

            ButtonEntar(
                title = "Anonimo",
                icon = R.drawable.sonara_logo,
                onClick = { }
            )
            ButtonEntar(
                title = "Login",
                icon = R.drawable.sonara_logo,
                onClick = { }
            )
        }
    }
}

