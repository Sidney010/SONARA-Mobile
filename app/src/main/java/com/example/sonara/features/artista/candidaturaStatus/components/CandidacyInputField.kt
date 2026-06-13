package com.example.sonara.features.artista.candidaturaStatus.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sonara.core.ui.theme.AppColors

@Composable
fun CandidacyInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Digite aqui...",
    enabled: Boolean = true,
    maxChar: Int? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    prefix: String? = null,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.colorFontLogin
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
                enabled = enabled,
                singleLine = singleLine,
                minLines = minLines,
                keyboardOptions = keyboardOptions,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                cursorBrush = SolidColor(Color(0xFFFFAA70)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top,
                            modifier = Modifier.weight(1f)
                        ) {
                            if (prefix != null && value.isNotEmpty()) {
                                Text(
                                    text = prefix,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Box {
                                if (value.isEmpty()) {
                                    Text(
                                        text = placeholder,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White.copy(alpha = 0.5f)
                                    )
                                }
                                innerTextField()
                            }
                        }
                        if (maxChar != null) {
                            Text(
                                text = "${value.length}/$maxChar",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White.copy(alpha = 0.6f),
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .align(if (singleLine) Alignment.CenterVertically else Alignment.Bottom)
                            )
                        }
                    }
                }
            )
        }
    }
}