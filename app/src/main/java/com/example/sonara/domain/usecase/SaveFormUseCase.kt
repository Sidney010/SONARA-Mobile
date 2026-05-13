package com.example.sonara.domain.usecase

import com.example.sonara.core.storage.FormData
import com.example.sonara.core.storage.FormDataStore

class SaveFormUseCase(
    private val store: FormDataStore
) {

    suspend operator fun invoke(
        data: FormData
    ) {

        store.saveForm(data)
    }
}