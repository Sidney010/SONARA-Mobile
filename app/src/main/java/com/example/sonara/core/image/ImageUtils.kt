package com.example.sonara.core.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface
import com.example.sonara.core.common.AppResult
import java.io.File
import java.io.FileOutputStream
import java.util.*

object ImageUtils {

    fun createTempImageUri(context: Context): Uri {
        // Usar o diretório de fotos padrão do App
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "IMG_${System.currentTimeMillis()}_",
            ".jpg",
            directory
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }

    suspend fun processImage(context: Context, uri: Uri): AppResult<Uri> {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: return AppResult.Error(Exception("Não foi possível abrir a imagem"))

            // Criar arquivo final no cache para o preview
            val outputFile = File(context.cacheDir, "profile_preview_${UUID.randomUUID()}.jpg")

            val bitmap = inputStream.use { input ->
                BitmapFactory.decodeStream(input)
            } ?: return AppResult.Error(Exception("Falha ao decodificar imagem"))

            // Corrigir rotação baseada nos metadados (importante para câmeras Samsung/Motorola)
            val rotatedBitmap = fixRotation(context, uri, bitmap)

            FileOutputStream(outputFile).use { out ->
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
            }

            AppResult.Success(outputFile.toUri())
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    private fun fixRotation(context: Context, uri: Uri, bitmap: Bitmap): Bitmap {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return bitmap
        val exif = ExifInterface(inputStream)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            else -> return bitmap
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}