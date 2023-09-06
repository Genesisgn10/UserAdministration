package com.example.useradministration
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText


class MaskTextWatcher(private val editText: EditText, private val mask: String) : TextWatcher {

    private var isFormatting = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // No implementation needed
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // No implementation needed
    }

    override fun afterTextChanged(editable: Editable?) {
        if (isFormatting) {
            return
        }

        isFormatting = true

        val text = editable.toString()

        if (text.length <= mask.length) {
            val formattedText = applyMask(text)
            editText.removeTextChangedListener(this)
            editText.setText(formattedText)
            editText.setSelection(formattedText.length)
            editText.addTextChangedListener(this)
        } else {
            editText.removeTextChangedListener(this)
            editText.setText(text.substring(0, mask.length))
            editText.setSelection(mask.length)
            editText.addTextChangedListener(this)
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
