package com.example.sonara.core.image

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import java.io.File

object ImageUtils {

    fun createTempImageUri(context: Context): Uri {
        val directory = context.externalCacheDir ?: context.cacheDir

        val file = File.createTempFile(
            "camera_",
            ".jpg",
            directory
        )

        return androidx.core.content.FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }

    fun copyToCache(context: Context, uri: Uri): Uri {
        val input = context.contentResolver.openInputStream(uri)
            ?: throw IllegalStateException("Não foi possível abrir input stream")

        val file = File.createTempFile(
            "image_",
            ".jpg",
            context.cacheDir
        )

        file.outputStream().use { output ->
            input.copyTo(output)
        }

        return file.toUri()
    }
}