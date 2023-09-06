package com.example.useradministration

import androidx.room.Room
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
import com.example.useradministration.presenter.ViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val serviceModule = module {
    single<UserRepositoryDatabase> { UserRepositoryDatabaseImpl(get(), get()) }
    single { DatabaseHelper(androidContext()) }
    single { AppDatabase.getDatabase(androidContext()) }

    factory<PostUserUseCase> { PostUser(get()) }
    factory { ViewModel(get(), get()) }

    factory<UserUseCase> { UserUserCaseImpl(get()) }

    factory<UserRepository> { UserRepositoryImpl(get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "DatabaseTest.db"
        ).build()
    }

    single { Service().createService(ServiceUser::class.java) }

    single { get<AppDatabase>().userDao() }
}