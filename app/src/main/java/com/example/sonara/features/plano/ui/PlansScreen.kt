package com.example.sonara.features.plano.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.features.plano.viewmodel.PlansViewModel

@Composable
fun PlansScreen(
    viewModel: PlansViewModel = hiltViewModel(),
    onNavigateToHome:    () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToLogin:   () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    ScreenContainer(
        verticalArrangement = Arrangement.SpaceBetween,
        verticalSpacing     = 6.dp,
        padding             = PaddingValues(12.dp, 40.dp)
    ) {
        HomeHeader(
            state = HeaderUiState(
                userName   = uiState.userName,
                userRole   = uiState.userRole,
                isLoggedIn = uiState.isLoggedIn
            ),
            onLogoClick          = onNavigateToHome,
            onAvatarClick        = { if (uiState.isLoggedIn) onNavigateToProfile() else onNavigateToLogin() },
            onNotificationClick  = {}
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text      = "Planos",
                textAlign = TextAlign.Center,
                color     = Color.White,
                fontSize  = 26.sp
            )
            PlanCard(
                title       = "Plano Diamante",
                textContent = "Tenha mais destaque na plataforma!\n\nCom o Plano Diamante, seu perfil aparece com mais frequência nas pesquisas, ganha mais visibilidade no feed e aumenta suas chances de ser reconhecido por empresas e recrutadores."
            )
            PlanCard(
                title       = "Plano Platina",
                textContent = "Tenha mais visibilidade e aumente sua presença na plataforma.\n\nApareça mais nas pesquisas e conquiste mais reconhecimento."
            )
        }
    }
}

@Composable
private fun PlanCard(title: String, textContent: String) {
    Card(
        modifier  = Modifier.fillMaxWidth().height(280.dp),
        shape     = RoundedCornerShape(24.dp),
        colors    = CardDefaults.cardColors(containerColor = AppColors.PrimaryOrange),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier            = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text       = title,
                color      = Color.White,
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign  = TextAlign.Center
            )
            Surface(
                modifier = Modifier.fillMaxWidth().weight(1f),
                color    = AppColors.colorOrangeMoreDark,
                shape    = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text     = textContent,
                    color    = Color.White,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}