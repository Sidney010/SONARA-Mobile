package com.example.sonara.features.inicial.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sonara.R
import com.example.sonara.core.ui.components.SonaraLogo
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.features.inicial.components.EnterButton
import com.example.sonara.features.inicial.components.WelcomeHeader

@Composable
fun StartBemVindoScreen(
    onNavigateToLogin: () -> Unit
) {
    ScreenContainer() {

        SonaraLogo()

        WelcomeHeader(
            title = "BEM-VINDO A SONARA",
            subtitle = "UNIFICANDO IDEIAS, REALIZANDO SONHOS"
        )

        Spacer(modifier = Modifier.height(32.dp))

        EnterButton(
            title = "Anonimo",
            icon = R.drawable.sonara_logo,
            onClick = { /*TODO*/ }
        )

        EnterButton(
            title = "Login",
            icon = R.drawable.sonara_logo,
            onClick = onNavigateToLogin
        )

    }
}

