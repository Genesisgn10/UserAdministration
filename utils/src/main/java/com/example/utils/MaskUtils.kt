package com.example.utils
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

interface MaskFormatter {
    fun format(text: String): String
}

class GenericMaskFormatter(private val mask: String) : MaskFormatter {
    override fun format(text: String): String {
        val cleanedText = text.filter { it.isDigit() }
        val formattedText = StringBuilder()

        var textIndex = 0
        mask.forEach { maskChar ->
            if (textIndex >= cleanedText.length) {
                return formattedText.toString()
            }
            if (maskChar == '#') {
                formattedText.append(cleanedText[textIndex++])
            } else {
                formattedText.append(maskChar)
            }
        }

        return formattedText.toString()
    }
}

object MaskUtils {
    private val textChangeListeners = mutableListOf<TextWatcher>()

    fun applyMaskToEditText(editText: EditText, mask: String) {
        removeAllTextChangedListeners(editText)

        val maskFormatter = GenericMaskFormatter(mask)

        val textWatcher = createTextWatcher(editText, maskFormatter, mask)
        textChangeListeners.add(textWatcher)
        editText.addTextChangedListener(textWatcher)
    }

    private fun createTextWatcher(editText: EditText, maskFormatter: MaskFormatter, mask: String) = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            val text = editable.toString()
            val formattedText = maskFormatter.format(text)

            if (formattedText != text) {
                editText.removeTextChangedListener(this)
                editText.setText(formattedText)
                editText.setSelection(formattedText.length)
                editText.addTextChangedListener(this)
            }

            editText.error = if (formattedText.length != mask.length) "Campo Inválido" else null
        }
    }

    private fun removeAllTextChangedListeners(editText: EditText) {
        for (listener in textChangeListeners) {
            editText.removeTextChangedListener(listener)
        }
        textChangeListeners.clear()
    }
}
