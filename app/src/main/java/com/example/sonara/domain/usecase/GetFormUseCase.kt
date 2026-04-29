package com.example.sonara.domain.usecase

import com.example.sonara.core.storage.FormDataStore

class GetFormUseCase(private val store: FormDataStore) {
    operator fun invoke() = store.getForm()
}