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

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun View.showSnackbar(@StringRes messageResId: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, messageResId, duration).show()
}