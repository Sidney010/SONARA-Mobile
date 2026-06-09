package com.example.sonara.features.cadastrar.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
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
    // Quantos steps existem no total
    val totalSteps = SignUpStep.entries.size  // 3

    // Progresso baseado no índice do step atual (1-indexed para UX)
    // PERSONAL_DATA(0) → 1/3, PROFILE_DATA(1) → 2/3, ADDRESS(2) → 3/3
    val progress = (currentStep.ordinal + 1).toFloat() / totalSteps.toFloat()

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "SignUpProgress"
    )

    val stepLabel = when (currentStep) {
        SignUpStep.PERSONAL_DATA -> "Dados Pessoais"
        SignUpStep.PROFILE_DATA  -> "Perfil"
        SignUpStep.ADDRESS        -> "Endereço"
    }

    // FIX: usar wrapContentWidth foi removido — o Column agora ocupa
    // fillMaxWidth garantido pelo modifier externo passado pelo SignUpCard.
    // O LinearProgressIndicator precisa de largura explícita para renderizar;
    // sem fillMaxWidth() ele colapsa para 0 quando está dentro de weight(fill=false).
    Column(
        modifier = modifier
            .fillMaxWidth()   // <-- crítico: garante que o indicator tenha largura
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stepLabel,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
            // Indicador textual "1 / 3" para acessibilidade e clareza
            Text(
                text = "${currentStep.ordinal + 1} / $totalSteps",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()   // <-- sem isso, o indicator não renderiza
                .height(8.dp),
            strokeCap = StrokeCap.Round
        )
    }
}