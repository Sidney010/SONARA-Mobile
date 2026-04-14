package com.example.sonara.features.login.components.logincard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ForgotPasswordRow(onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Esqueceu a senha? ")
        Text(
            text = "Clique aqui",
            modifier = Modifier.clickable { onClick() }
        )
    }
}