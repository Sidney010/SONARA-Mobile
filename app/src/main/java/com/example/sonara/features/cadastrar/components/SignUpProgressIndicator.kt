package com.example.sonara.features.cadastrar.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sonara.features.cadastrar.model.SignUpStep

@Composable
fun SignUpProgressIndicator(
    currentStep: SignUpStep,
    modifier: Modifier = Modifier
) {
    val progress = when (currentStep) {
        SignUpStep.PERSONAL_DATA -> 0.33f
        SignUpStep.PROFILE_DATA -> 0.66f
        SignUpStep.ADDRESS -> 1f
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "SignUpProgress"
    )

    val stepTitle = when (currentStep) {
        SignUpStep.PERSONAL_DATA -> "Dados Pessoais"
        SignUpStep.PROFILE_DATA -> "Perfil"
        SignUpStep.ADDRESS -> "Endereço"
    }

    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Text(
            text = stepTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            strokeCap = StrokeCap.Round
        )
    }
}
