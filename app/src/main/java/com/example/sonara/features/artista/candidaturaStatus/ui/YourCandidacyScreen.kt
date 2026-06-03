package com.example.sonara.features.artista.candidaturaStatus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.header.HeaderUiState
import com.example.sonara.core.ui.components.header.HomeHeader
import com.example.sonara.core.ui.theme.DarkGradients
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.example.sonara.core.ui.theme.AppColors

@Composable
fun YourCandidacyScreen(modifier: Modifier = Modifier) {

    val gradients = DarkGradients
    var cacheEsperado by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradients.secondaryCard)
    ) {
        ScreenContainer(
            modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            verticalSpacing = 6.dp,
            padding = PaddingValues(start = 12.dp, end = 12.dp, top = 40.dp, bottom = 130.dp)
        ) {

            HomeHeader(
                state = HeaderUiState(
                    userName = "UserName",
                    userRole = "TipodeUsuario"
                ),
                onLogoClick = {},
                onAvatarClick = {},
                onNotificationClick = {}
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        brush = gradients.secondaryCard,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding( bottom = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.colorCard.copy(0.5f))
            ) {
                Column {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Text(
                            text = "Meus Eventos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppColors.colorFontLogin
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .background(
                                    Color.LightGray.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = AppColors.SecondColor,
                                modifier = Modifier.size(48.dp).align(Alignment.Center)
                            )

                        }
                    }

                    Column(
                        modifier = Modifier.padding(12.dp).align(Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .width(345.dp)
                                .wrapContentHeight()
                                .background(
                                    AppColors.PrimaryColor.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(start =10.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 3.dp),
                                text = "Nosso evento de música promete uma noite cheia de energia, apresentações ao vivo e muita diversão. Um espaço pensado para reunir pessoas," +
                                        " criar momentos especiais e celebrar a música em um ambiente animado e inesquecível.\n",
                                fontSize = 15.sp,
                                color = AppColors.colorFontLogin
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .size(345.dp)
                        .background(
                            AppColors.PrimaryColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Column() {

                        Column(
                            modifier = Modifier.padding(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {

                            CacheInputField(
                                label = "Cachê Esperado:",
                                value = cacheEsperado,
                                onValueChange = { cacheEsperado = it },
                                placeholder = "Digite aqui..."
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.White.copy(alpha = 0.12f))
                        )

                        Column(
                            modifier = Modifier.padding(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Cachê Ofertado:",
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.55f)
                            )
                            Text(
                                text = "(Vem do Banco o valor)",
                                fontSize = 14.sp,
                                color = Color(0xFFFFAA70).copy(alpha = 0.6f)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.White.copy(alpha = 0.12f))
                        )

                        Column(
                            modifier = Modifier.padding(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Status:",
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.55f)
                            )
                            Text(
                                text = "(Muda de acordo com o status do Evento)",
                                fontSize = 14.sp,
                                color = Color(0xFFFFAA70).copy(alpha = 0.6f)
                            )
                        }


                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.White.copy(alpha = 0.12f))
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "DATA",
                                    fontSize = 13.sp,
                                    color = Color.White.copy(alpha = 0.55f)
                                )
                                Text(
                                    text = "DD/MM/AAAA",
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "HORA",
                                    fontSize = 13.sp,
                                    color = Color.White.copy(alpha = 0.55f)
                                )
                                Text(
                                    text = "00:00",
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CacheInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Digite aqui...",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.White.copy(alpha = 0.55f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.LightGray.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = AppColors.colorFontLogin.copy(0.9f)
                ),
                cursorBrush = SolidColor(Color(0xFFFFAA70)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp, vertical = 2.dp),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = placeholder,
                            fontSize = 14.sp,
                            color = Color(0xFFFFAA70).copy(alpha = 0.6f)
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}