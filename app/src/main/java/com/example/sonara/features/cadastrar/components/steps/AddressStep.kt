package com.example.sonara.features.cadastrar.components.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sonara.core.ui.components.AppTextField

@Composable
fun AddressStep(
    cep: String, cepError: String?, onCepChange: (String) -> Unit,
    rua: String, ruaError: String?, onRuaChange: (String) -> Unit,
    bairro: String, bairroError: String?, onBairroChange: (String) -> Unit,
    cidade: String, cidadeError: String?, onCidadeChange: (String) -> Unit,
    uf: String, ufError: String?, onUfChange: (String) -> Unit,
    numero: String, onNumeroChange: (String) -> Unit,
    complemento: String, onComplementoChange: (String) -> Unit,
    isLoadingCep: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        AppTextField(
            value = cep, onValueChange = onCepChange,
            placeholder = "CEP *",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = cepError != null,
            errorMessage = cepError,
            trailingContent = if (isLoadingCep) {
                { CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp) }
            } else null
        )
        AppTextField(
            value = rua, onValueChange = onRuaChange, placeholder = "Rua",
            isError = ruaError != null, errorMessage = ruaError
        )
        AppTextField(
            value = bairro, onValueChange = onBairroChange, placeholder = "Bairro",
            isError = bairroError != null, errorMessage = bairroError
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppTextField(
                value = cidade, onValueChange = onCidadeChange, placeholder = "Cidade", 
                modifier = Modifier.weight(2f),
                isError = cidadeError != null, errorMessage = cidadeError
            )
            AppTextField(
                value = uf, onValueChange = onUfChange, placeholder = "UF", 
                modifier = Modifier.weight(1f),
                isError = ufError != null, errorMessage = ufError
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppTextField(
                value = numero, onValueChange = onNumeroChange, placeholder = "Número", 
                modifier = Modifier.weight(1f), 
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            AppTextField(
                value = complemento, onValueChange = onComplementoChange, 
                placeholder = "Complemento", modifier = Modifier.weight(2f)
            )
        }
    }
}
