package com.example.useradministration

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream

fun Bitmap.toBase64(quality: Int = 100): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun String.fromBase64(): Bitmap? {
    val decodedBytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}


fun Bitmap.resizeToMaxSize(maxSize: Long): Bitmap {
    val stream = ByteArrayOutputStream()
    var quality = 100
    do {
        stream.reset()
        this.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        quality -= 10
    } while (stream.toByteArray().size > maxSize && quality > 0)
    return BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().size)
}

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun View.showSnackbar(@StringRes messageResId: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, messageResId, duration).show()
}