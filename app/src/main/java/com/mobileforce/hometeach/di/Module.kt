package com.mobileforce.hometeach.di

import androidx.room.Room
import com.mobileforce.hometeach.AppConstants.BASE_URL
import com.mobileforce.hometeach.AppConstants.DATABASE_NAME
import com.mobileforce.hometeach.data.UserRepositoryImpl
import com.mobileforce.hometeach.data.repo.UserRepository
import com.mobileforce.hometeach.data.sources.DataSource
import com.mobileforce.hometeach.data.sources.DataSourceFactory
import com.mobileforce.hometeach.data.sources.LocalDataSource
import com.mobileforce.hometeach.data.sources.RemoteDataSource
import com.mobileforce.hometeach.localsource.AppDataBase
import com.mobileforce.hometeach.localsource.PreferenceHelper
import com.mobileforce.hometeach.remotesource.Api
import com.mobileforce.hometeach.ui.signin.SignInViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    single {
        PreferenceHelper(
            androidContext()
        )
    }


    single<Api> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            //TODO add http logger
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
    }


    single {
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java, DATABASE_NAME
        ).build()

    }

    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    single { DataSourceFactory(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }


    //---------------------view models --------------------------------------

    factory<SignInViewModel> { get() }
}