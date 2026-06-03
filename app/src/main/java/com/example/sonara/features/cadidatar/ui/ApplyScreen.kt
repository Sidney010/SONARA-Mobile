package com.example.sonara.features.cadidatar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.AppColors

@Composable
fun ApplyScreen(modifier: Modifier = Modifier) {

    var conteText by remember { mutableStateOf("") }
    var valorText by remember { mutableStateOf("") }

    ScreenContainer(
        verticalArrangement = Arrangement.SpaceBetween,
        verticalSpacing = 6.dp,
        padding = PaddingValues(12.dp, 40.dp)
    ) {
        val headerState = HeaderUiState(
            userName = "UserName",
            userRole = "TipodeUsuario"
        )

        HomeHeader(
            state = headerState,
            onLogoClick = {},
            onAvatarClick = {},
            onNotificationClick = {}
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Candidatura",
                fontSize = 24.sp,
                color = AppColors.colorFontLogin
            )

            // Correção: ambos usam o mesmo componente com fillMaxWidth
            ChatInputCard(
                label = "Conte sobre Você",
                value = conteText,
                onValueChange = { conteText = it }
            )

            ChatInputCard(
                label = "Qual valor Ofertado",
                value = valorText,
                onValueChange = { valorText = it }
            )
        }

        Button(
            onClick = { /* TODO: ação de envio */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(top = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.colorFontLogin,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "se Candidatar",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ChatInputCard(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Correção: era width(50.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.SecondColor.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = AppColors.colorFontLogin
            )

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                placeholder = {
                    Text(
                        text = "Digite aqui...",
                        color = AppColors.SecondColor.copy(alpha = 0.4f),
                        fontSize = 14.sp
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = AppColors.PrimaryColor,
                    unfocusedContainerColor = AppColors.PrimaryColor.copy(alpha = 0.5f),
                    focusedIndicatorColor = AppColors.colorFontLogin,
                    unfocusedIndicatorColor = Color.White,
                    cursorColor = AppColors.colorFontLogin
                ),
                shape = RoundedCornerShape(8.dp),
                maxLines = 1
            )
        }
    }
}