package com.example.utils

import android.util.Patterns

object ValidationUtils {

    fun isValidPassword(password: String): Boolean {
        val passwordRegex =
            "^(?=.*[A-Z])(?=.*\\d).{8,}$"
        return password.matches(passwordRegex.toRegex())
    }

    fun isValidName(name: String): Boolean {
        return name.length >= 30
    }

    fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}
