package com.example.sonara.features.plano.ui

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.AppColors
import com.example.sonara.core.ui.theme.LocalGradients

@Composable
fun PlansScreen(modifier: Modifier = Modifier) {

    val gradients = LocalGradients.current


    ScreenContainer(
        verticalArrangement = Arrangement.SpaceBetween,
        verticalSpacing = 6.dp,
        padding = PaddingValues(12.dp,40.dp)
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
            modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                modifier = Modifier,
                text = ("Planos"),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 26.sp

            )
            PlanCard(title = "Plano diamante", textConent = "Plano Platina")

        }
    }
}

@Composable
fun PlanCard(title: String, textConent: String) {

    val gradients = LocalGradients.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(top = 20.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.PrimaryOrange),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center

            )

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = AppColors.colorOrangeMoreDark,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Tenha mais destaque na plataforma!\n" +
                            "Com o Plano Diamante, seu perfil aparece com mais frequência nas pesquisas," +
                            " ganha mais visibilidade no feed e" +
                            " aumenta suas chances de ser reconhecido por empresas e recrutadores.",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Start
                )
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(top = 20.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.colorOrangeDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = textConent,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center

            )

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = AppColors.colorOrangeMoreDark,
                shape = RoundedCornerShape(16.dp),

                ) {
                Text(
                    text = "Tenha mais visibilidade e aumente sua presença na plataforma.\n" +
                            "Apareça mais nas pesquisas e conquiste mais reconhecimento.",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}