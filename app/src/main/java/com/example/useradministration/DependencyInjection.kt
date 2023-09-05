package com.example.useradministration

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val serviceModule = module {
    single<UserRepository2> { UserRepositoryImpl(get(), get()) }
    single { DatabaseHelper(androidContext()) }
    single { AppDatabase.getDatabase(androidContext()) }

    factory { ViewModel(get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "DatabaseTest.db"
        ).build()
    }

    single { get<AppDatabase>().userDao() }
}