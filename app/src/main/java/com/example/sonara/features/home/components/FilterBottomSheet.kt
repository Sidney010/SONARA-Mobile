package com.example.sonara.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.sonara.features.home.model.EventoFiltro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    filtroAtivo: EventoFiltro,
    onFiltroSelected: (EventoFiltro) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(
                text  = "Filtrar / Ordenar",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )

            HorizontalDivider()

            FilterOption(
                icon        = Icons.Outlined.Sort,
                label       = "Padrão",
                description = "Ordem original dos eventos",
                isSelected  = filtroAtivo == EventoFiltro.PADRAO,
                onClick     = { onFiltroSelected(EventoFiltro.PADRAO) }
            )
            FilterOption(
                icon        = Icons.Outlined.CalendarToday,
                label       = "Por data",
                description = "Eventos mais próximos primeiro",
                isSelected  = filtroAtivo == EventoFiltro.POR_DATA,
                onClick     = { onFiltroSelected(EventoFiltro.POR_DATA) }
            )
            FilterOption(
                icon        = Icons.Outlined.Schedule,
                label       = "Por hora",
                description = "Ordenado pelo horário de início",
                isSelected  = filtroAtivo == EventoFiltro.POR_HORA,
                onClick     = { onFiltroSelected(EventoFiltro.POR_HORA) }
            )
            FilterOption(
                icon        = Icons.Outlined.MusicNote,
                label       = "Com artista",
                description = "Apenas eventos com artista confirmado",
                isSelected  = filtroAtivo == EventoFiltro.POR_ARTISTA,
                onClick     = { onFiltroSelected(EventoFiltro.POR_ARTISTA) }
            )
            FilterOption(
                icon        = Icons.Outlined.LocationOn,
                label       = "Por localização",
                description = "Ordenado por cidade",
                isSelected  = filtroAtivo == EventoFiltro.POR_LOCALIZACAO,
                onClick     = { onFiltroSelected(EventoFiltro.POR_LOCALIZACAO) }
            )
        }
    }
}

@Composable
private fun FilterOption(
    icon: ImageVector,
    label: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text  = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text  = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
        if (isSelected) {
            RadioButton(selected = true, onClick = onClick)
        }
    }
}