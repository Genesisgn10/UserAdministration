package com.example.useradministration

import androidx.room.Room
import com.example.database.AppDatabase
import com.example.database.DatabaseHelper
import com.example.network.Service
import com.example.useradministration.data.ServiceUser
import com.example.useradministration.data.UserRepository
import com.example.useradministration.data.UserRepositoryDatabase
import com.example.useradministration.data.UserRepositoryDatabaseImpl
import com.example.useradministration.data.UserRepositoryImpl
import com.example.useradministration.domain.PostUser
import com.example.useradministration.domain.PostUserUseCase
import com.example.useradministration.domain.UserUseCase
import com.example.useradministration.domain.UserUserCaseImpl
import com.example.useradministration.presenter.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val serviceModule = module {
    single { DatabaseHelper(androidContext()) }
    single { AppDatabase.getDatabase(androidContext()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "DatabaseTest.db"
        ).build()
    }
    single { get<AppDatabase>().userDao() }
    single { Service().createService(ServiceUser::class.java) }

    factory<PostUserUseCase> { PostUser(get()) }
    factory { UserViewModel(get(), get()) }
    factory<UserRepositoryDatabase> { UserRepositoryDatabaseImpl(get(), get()) }
    factory<UserUseCase> { UserUserCaseImpl(get()) }
    factory<UserRepository> { UserRepositoryImpl(get()) }
}