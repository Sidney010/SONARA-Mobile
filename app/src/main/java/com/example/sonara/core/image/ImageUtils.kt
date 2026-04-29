package com.example.sonara.core.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.exifinterface.media.ExifInterface
import com.example.sonara.core.common.AppResult
import java.io.File
import java.io.FileOutputStream

object ImageUtils {

    fun createTempImageUri(context: Context): Uri {
        val directory = context.getExternalFilesDir("images")!!

        val file = File.createTempFile(
            "camera_",
            ".jpg",
            directory
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }

    fun processImage(context: Context, uri: Uri): AppResult<Uri> {
        return try {

            // 1. Copiar para cache
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: return AppResult.Error(Exception("InputStream null"))

            val tempFile = File.createTempFile("raw_", ".jpg", context.cacheDir)

            inputStream.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            // 2. Decode bitmap
            val bitmap = BitmapFactory.decodeFile(tempFile.absolutePath)
                ?: return AppResult.Error(Exception("Bitmap null"))

            // 3. Corrigir rotação
            val rotatedBitmap = fixRotation(tempFile, bitmap)

            // 4. Compressão
            val finalFile = File.createTempFile("final_", ".jpg", context.cacheDir)

            FileOutputStream(finalFile).use { out ->
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
            }

            AppResult.Success(finalFile.toUri())

        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    private fun fixRotation(file: File, bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(file.absolutePath)

        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )

        val matrix = Matrix()

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        }

        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
}