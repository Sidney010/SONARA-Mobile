package com.example.sonara.features.login.components.logincard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SignUpRow(onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Não tem conta? ")
        Text(
            text = "Cadastre-se",
            modifier = Modifier.clickable { onClick() }
        )
    }
}