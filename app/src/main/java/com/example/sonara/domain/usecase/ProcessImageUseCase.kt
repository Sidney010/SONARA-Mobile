package com.example.sonara.domain.usecase

import android.content.Context
import android.net.Uri
import com.example.sonara.core.common.AppResult
import com.example.sonara.core.image.ImageUtils

class ProcessImageUseCase {

    suspend operator fun invoke(context: Context, uri: Uri): AppResult<Uri> {
        return ImageUtils.processImage(context, uri)
    }
}