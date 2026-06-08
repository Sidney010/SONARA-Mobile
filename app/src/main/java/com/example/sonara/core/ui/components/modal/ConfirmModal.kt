package com.example.sonara.core.ui.components.modal

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.sonara.core.ui.theme.AppColors

@Composable
fun ConfirmModal(
    title: String,
    message: String,
    confirmText: String = "Confirmar",
    cancelText: String = "Cancelar",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, color = AppColors.colorFontLogin) },
        text = { Text(text = message, color = AppColors.colorFontLogin.copy(alpha = 0.8f)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText, color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = cancelText, color = AppColors.SecondColor)
            }
        },
        containerColor = AppColors.colorCard
    )
}