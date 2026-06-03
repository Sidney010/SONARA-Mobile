package com.example.sonara.features.inicial.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeHeader(
    title: String,
    subtitle: String,
    color: Color,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                val sonaraIndex = title.indexOf("SONARA")
                if (sonaraIndex >= 0) {
                    append(title.substring(0, sonaraIndex))
                    withStyle(SpanStyle(color = color)) {
                        append("SONARA")
                    }
                    append(title.substring(sonaraIndex + 6))
                } else {
                    append(title)
                }
            },
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}

//fun WelcomeHeader(
//    color: Color,
//    title: String,
//    subtitle: String
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        Text(
//            text = title,
//            style = MaterialTheme.typography.titleLarge,
//            color = MaterialTheme.colorScheme.primary
//        )
//        Text(
//            text = subtitle,
//            style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.primary
//        )
//    }
//}