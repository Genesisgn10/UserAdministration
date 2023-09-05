package com.example.useradministration

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MyAppDatabase2.db"
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
        const val COLUMN_PHOTO_URL = "photoUrl"  // Removendo a NOT NULL aqui
        const val COLUMN_ADDRESS = "address"

        // Criação da tabela de usuários
        private const val CREATE_USERS_TABLE = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_USERNAME TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_BIRTHDATE TEXT,
                $COLUMN_SEX TEXT,
                $COLUMN_TYPE TEXT,
                $COLUMN_CPF_CNPJ TEXT,
                $COLUMN_PHOTO_URL TEXT,
                $COLUMN_ADDRESS TEXT
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Atualizações do esquema, se necessário
        // Você pode implementar isso de acordo com sua necessidade
    }
}
