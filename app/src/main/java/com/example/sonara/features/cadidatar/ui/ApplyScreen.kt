package com.example.sonara.features.cadidatar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader

@Composable
fun ApplyScreen(modifier: Modifier = Modifier) {

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

        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card(
                modifier = Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ){
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(40.dp)
                        .background(Color.Blue)
                ){
                    Text(
                        text = ("Conte sobre Você:"),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}