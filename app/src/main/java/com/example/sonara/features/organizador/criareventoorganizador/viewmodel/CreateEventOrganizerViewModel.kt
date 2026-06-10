package com.example.sonara.features.organizador.criareventoorganizador.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sonara.core.auth.TokenManager
import com.example.sonara.core.common.AppResult
import com.example.sonara.data.remote.dto.request.EventoCreateRequestDto
import com.example.sonara.domain.usecase.BuscarEnderecoPorCepUseCase
import com.example.sonara.domain.usecase.CreateEventUseCase
import com.example.sonara.domain.usecase.UploadFotoEventoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject

data class CreateEventUiState(
    val nome: String = "",
    val descricao: String = "",
    val local: String = "",
    val data: String = "",         // "yyyy-MM-dd"
    val horaInicio: String = "",    // "HH:mm:ss"
    val horaFim: String = "",       // "HH:mm:ss"
    val cep: String = "",
    val logradouro: String = "",
    val numero: String = "",
    val bairro: String = "",
    val cidade: String = "",
    val estado: String = "",
    val complemento: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val fotoUris: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val isCepLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val userName: String = "Anônimo",
    val userRole: String = "Organizador",
    val userPhoto: String? = null
)

@HiltViewModel
class CreateEventOrganizerViewModel @Inject constructor(
    private val createEventUseCase: CreateEventUseCase,
    private val uploadFotoUseCase: UploadFotoEventoUseCase,
    private val buscarEnderecoUseCase: BuscarEnderecoPorCepUseCase,
    private val tokenManager: TokenManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateEventUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(
                userName = tokenManager.userName.first() ?: "Anônimo",
                userRole = tokenManager.userType.first() ?: "Organizador",
                userPhoto = tokenManager.userPhoto.first()
            ) }
        }
    }

    fun onNomeChange(value: String) = _uiState.update { it.copy(nome = value) }
    fun onDescricaoChange(value: String) = _uiState.update { it.copy(descricao = value) }
    fun onLocalChange(value: String) = _uiState.update { it.copy(local = value) }
    fun onDataChange(value: String) = _uiState.update { it.copy(data = value) }
    fun onHoraInicioChange(value: String) = _uiState.update { it.copy(horaInicio = value) }
    fun onHoraFimChange(value: String) = _uiState.update { it.copy(horaFim = value) }
    fun onNumeroChange(value: String) = _uiState.update { it.copy(numero = value) }
    fun onComplementoChange(value: String) = _uiState.update { it.copy(complemento = value) }
    fun onLocationChange(lat: Double, lng: Double) = _uiState.update { it.copy(latitude = lat, longitude = lng) }

    fun onCepChange(value: String) {
        _uiState.update { it.copy(cep = value) }
        if (value.length == 8) {
            buscarEndereco(value)
        }
    }

    private fun buscarEndereco(cep: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isCepLoading = true) }
            when (val result = buscarEnderecoUseCase(cep)) {
                is AppResult.Success -> {
                    val endereco = result.data
                    _uiState.update { it.copy(
                        logradouro = endereco.rua,
                        bairro = endereco.bairro,
                        cidade = endereco.cidade,
                        estado = endereco.uf,
                        isCepLoading = false
                    ) }
                }
                is AppResult.Error -> {
                    _uiState.update { it.copy(isCepLoading = false) }
                }
            }
        }
    }

    fun onFotosSelected(uris: List<Uri>) {
        _uiState.update { it.copy(fotoUris = (it.fotoUris + uris).distinct().take(3)) }
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, errorMessage = null) }
    }

    fun createEvent() {
        val state = _uiState.value
        if (state.nome.isBlank() || state.data.isBlank() || state.horaInicio.isBlank() || state.local.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Preencha os campos obrigatórios") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val organizadorId = tokenManager.userId.first()?.toIntOrNull() ?: 0
            val request = EventoCreateRequestDto(
                evento_nome = state.nome,
                descricao = state.descricao,
                local = state.local,
                data = state.data,
                hora_inicio = if (state.horaInicio.length == 5) "${state.horaInicio}:00" else state.horaInicio,
                hora_fim = if (state.horaFim.length == 5) "${state.horaFim}:00" else state.horaFim,
                cep = state.cep,
                logradouro = state.logradouro,
                numero = state.numero,
                complemento = state.complemento,
                bairro = state.bairro,
                cidade = state.cidade,
                estado = state.estado,
                organizador_id = organizadorId,
                latitude = state.latitude,
                longitude = state.longitude
            )

            when (val result = createEventUseCase(request)) {
                is AppResult.Success -> {
                    val eventoId = result.data
                    uploadFotos(eventoId)
                }
                is AppResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.exception.message ?: "Erro desconhecido") }
                }
            }
        }
    }

    private suspend fun uploadFotos(eventoId: Int) {
        val uris = _uiState.value.fotoUris
        if (uris.isEmpty()) {
            _uiState.update { it.copy(isLoading = false, successMessage = "Evento criado com sucesso!") }
            return
        }

        val uploadJobs = uris.map { uri ->
            viewModelScope.async {
                prepareFilePart(uri)?.let { part ->
                    uploadFotoUseCase(eventoId, part)
                }
            }
        }

        val results = uploadJobs.awaitAll()
        val failures = results.count { it is AppResult.Error }

        if (failures > 0) {
            _uiState.update { it.copy(
                isLoading = false,
                successMessage = "Evento criado, mas $failures foto(s) falharam no upload."
            ) }
        } else {
            _uiState.update { it.copy(isLoading = false, successMessage = "Evento criado com sucesso!") }
        }
    }

    private fun prepareFilePart(uri: Uri): MultipartBody.Part? {
        return try {
            val file = uriToFile(uri)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("foto", file.name, requestFile)
        } catch (e: Exception) {
            null
        }
    }

    private fun uriToFile(uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_image_${UUID.randomUUID()}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }
}