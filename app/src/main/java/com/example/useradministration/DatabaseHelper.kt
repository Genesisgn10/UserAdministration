package com.example.useradministration

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class DatabaseHelper(val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "DatabaseTest.db"
        private const val DATABASE_VERSION = 1

        // Tabela de usuários
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_BIRTHDATE = "birthdate"
        const val COLUMN_SEX = "sex"
        const val COLUMN_TYPE = "type"
        const val COLUMN_CPF_CNPJ = "cpf_cnpj"
        const val COLUMN_PHOTO_URL = "photoUrl"
        const val COLUMN_ADDRESS = "address"

        // Criação da tabela de usuários
        private const val CREATE_USERS_TABLE = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_USERNAME TEXT NOT NULL UNIQUE,
                $COLUMN_PASSWORD TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL,
                $COLUMN_BIRTHDATE TEXT NOT NULL,
                $COLUMN_SEX TEXT NOT NULL,
                $COLUMN_TYPE TEXT NOT NULL,
                $COLUMN_CPF_CNPJ TEXT NOT NULL,
                $COLUMN_PHOTO_URL TEXT NOT NULL,
                $COLUMN_ADDRESS TEXT NOT NULL
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        executeSqlScriptFromAssets(db, "stored_procedures.sql")
        db.execSQL(CREATE_USERS_TABLE)
    }

    // Método para executar um script SQL a partir de um arquivo na pasta assets
    private fun executeSqlScriptFromAssets(db: SQLiteDatabase, fileName: String) {
        try {
            val inputStream = context.assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val statements = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                statements.append(line)
            }
            val script = statements.toString()
            val statementsArray = script.split(";".toRegex()).toTypedArray()
            for (sqlStatement in statementsArray) {
                db.execSQL(sqlStatement)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Atualizações do esquema, se necessário
        // Você pode implementar isso de acordo com sua necessidade
    }
}