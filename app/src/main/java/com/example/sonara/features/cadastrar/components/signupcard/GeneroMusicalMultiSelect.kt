package com.example.sonara.features.cadastrar.components.signupcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.sonara.domain.model.GeneroMusical

@Composable
fun GeneroMusicalMultiSelect(
    generos: List<GeneroMusical>,
    selected: Set<Int>,
    onToggle: (Int) -> Unit,
    isError: Boolean      = false,
    errorMessage: String? = null,
    modifier: Modifier    = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text  = "Gêneros musicais *",
            style = MaterialTheme.typography.labelMedium,
            color = if (isError) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Grid 2 colunas — altura fixa com scroll interno para não quebrar o scroll do card
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement   = Arrangement.spacedBy(0.dp)
        ) {
            items(generos, key = { it.id }) { genero ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked         = genero.id in selected,
                        onCheckedChange = { onToggle(genero.id) }
                    )
                    Text(
                        text  = genero.nome,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        if (isError && errorMessage != null) {
            Text(
                text     = errorMessage,
                color    = MaterialTheme.colorScheme.error,
                style    = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}