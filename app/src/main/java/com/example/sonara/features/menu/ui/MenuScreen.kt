package com.example.sonara.features.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sonara.core.layout.ScreenContainer
import com.example.sonara.core.ui.components.SonaraLogoSVG

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier
) {
    ScreenContainer (
        verticalSpacing = 20.dp,
        padding = PaddingValues(12.dp,40.dp)
    ) {

        // Componente Header
        Row(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            SonaraLogoSVG(
                modifier = Modifier.fillMaxHeight(0.6f),
            )
            Row(
                modifier = Modifier.fillMaxHeight().fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text( text = "Sidney Campos Aragão", color = Color.White, textAlign = TextAlign.Center)
                    Text( text = "Usuário", color = Color.White, textAlign = TextAlign.Center)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .clickable { /*TODO*/ },
                                contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            tint = Color.White,
                            contentDescription = "Placeholder"
                        )
                    }
                }
            }
        }

        // Search
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {/*TODO*/}
        )
    }

}

