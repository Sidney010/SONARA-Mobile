package com.example.sonara.features.inicial.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.sonara.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.theme.AppColors
import com.example.sonara.core.theme.AppGradients
import com.example.sonara.features.inicial.components.ButtonEntar

@Composable
fun StartBemVindoScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppGradients.PrimaryBackground),
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
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "BEM-VINDO A SONARA",
                    color = AppColors.PrimaryOrange,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,

                    )
                Text(
                    text = "UNIFICANDO IDEIAS, REALIZANDO SONHOS",
                    color = AppColors.PrimaryOrange,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
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

