package com.example.utils

import android.util.Patterns

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object ValidationUtils {

    fun isValidPassword(password: String): Boolean {
        val passwordRegex = "^(?=.*[A-Z])(?=.*\\d).{8,}$"
        return password.matches(passwordRegex.toRegex())
    }

    fun isValidName(name: String): Boolean {
        return name.length >= 30
    }

    fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun calculateAgeFromBirthdate(dataNascimento: String): Int {
        if (dataNascimento.isBlank()) {
            return -1
        }

        val formato = SimpleDateFormat("dd/MM/yyyy")
        val dataNasc = formato.parse(dataNascimento) ?: return -1

        val dataAtual = Calendar.getInstance().time

        val calendarNasc = Calendar.getInstance()
        calendarNasc.time = dataNasc

        var idade = Calendar.getInstance().get(Calendar.YEAR) - calendarNasc.get(Calendar.YEAR)

        if (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) < calendarNasc.get(Calendar.DAY_OF_YEAR)) {
            idade--
        }

        return idade
    }

    fun dateOfBirthToTimestamp(dateOfBirth: String): Long {
        return try {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = dateFormat.parse(dateOfBirth)
            date.time
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun timestampToDate(timestamp: Long, pattern: String = "dd/MM/yyyy"): String {
        return try {
            val dateFormat = SimpleDateFormat(pattern)
            val date = Date(timestamp)
            dateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

}
