package com.example.useradministration

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val serviceModule = module {
    single<UserRepository2> { UserRepositoryImpl(get()) }
    single { DatabaseHelper(androidContext()) }

}