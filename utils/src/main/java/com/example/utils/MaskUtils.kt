package com.example.utils
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MaskTextWatcher(private val editText: EditText, private val mask: String) : TextWatcher {

    private var isFormatting = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable?) {
        if (isFormatting) {
            return
        }

        isFormatting = true

        val text = editable.toString()
        val formattedText = applyMask(text)

        if (formattedText != text) {
            editText.removeTextChangedListener(this)
            editText.setText(formattedText)
            editText.setSelection(formattedText.length)
            editText.addTextChangedListener(this)
        }

        if (formattedText.length != mask.length) {
            editText.error = "Tamanho inválido"
        } else {
            editText.error = null
        }

        isFormatting = false
    }

    private fun applyMask(text: String): String {
        val cleanedText = text.replace("[^0-9]".toRegex(), "")
        val formattedText = StringBuilder()
        var textIndex = 0

        for (i in mask.indices) {
            if (textIndex >= cleanedText.length) {
                break
            }

            val maskChar = mask[i]
            if (maskChar == '#') {
                formattedText.append(cleanedText[textIndex])
                textIndex++
            } else {
                formattedText.append(maskChar)
            }
        }

        return formattedText.toString()
    }
}