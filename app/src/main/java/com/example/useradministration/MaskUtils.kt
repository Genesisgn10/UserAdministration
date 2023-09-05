package com.example.useradministration

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

class MaskUtils {
    companion object {
        fun applyCpfMask(editText: TextInputEditText) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val text = s.toString()
                    if (text.length == 3 || text.length == 7) {
                        if (text.length < 11) {
                            editText.setText("$text.")
                            editText.setSelection(editText.text?.length ?: 0)
                        }
                    } else if (text.length == 11) {
                        editText.setText("$text-")
                        editText.setSelection(editText.text?.length ?: 0)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun afterTextChanged(s: Editable?) {}
            })

            editText.filters = arrayOf(android.text.InputFilter.LengthFilter(14))
        }

        fun applyCnpjMask(editText: TextInputEditText) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val text = s.toString()
                    if (text.length == 2 || text.length == 6) {
                        if (text.length < 14) {
                            editText.setText("$text.")
                            editText.setSelection(editText.text?.length ?: 0)
                        }
                    } else if (text.length == 10) {
                        editText.setText("$text/")
                        editText.setSelection(editText.text?.length ?: 0)
                    } else if (text.length == 15) {
                        editText.setText("$text-")
                        editText.setSelection(editText.text?.length ?: 0)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun afterTextChanged(s: Editable?) {}
            })

            editText.filters = arrayOf(android.text.InputFilter.LengthFilter(18))
        }
    }
}