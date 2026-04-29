package com.example.sonara.domain.usecase

import com.example.sonara.core.storage.FormDataStore

class ClearFormUseCase(private val store: FormDataStore) {
    suspend operator fun invoke() = store.clear()
}