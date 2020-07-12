package com.mobileforce.hometeach.di

import androidx.room.Room
import com.mobileforce.hometeach.AppConstants.BASE_URL
import com.mobileforce.hometeach.AppConstants.DATABASE_NAME
import com.mobileforce.hometeach.data.UserRepositoryImpl
import com.mobileforce.hometeach.data.repo.UserRepository
import com.mobileforce.hometeach.data.sources.DataSourceFactory
import com.mobileforce.hometeach.data.sources.LocalDataSource
import com.mobileforce.hometeach.data.sources.RemoteDataSource
import com.mobileforce.hometeach.localsource.AppDataBase
import com.mobileforce.hometeach.localsource.PreferenceHelper
import com.mobileforce.hometeach.remotesource.Api
import com.mobileforce.hometeach.ui.RecoverPasswordViewModel

import com.mobileforce.hometeach.ui.home.HomePageViewModel
import com.mobileforce.hometeach.ui.signin.SignInViewModel
import com.mobileforce.hometeach.ui.signup.SignUpViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModule = module {

    single {
        PreferenceHelper(
            androidContext()
        )
    }


    single<Api> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
    }

    single<OkHttpClient> {

        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        httpClient
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

    factory { SignInViewModel(get(), get()) }
    factory { SignUpViewModel(get()) }
    factory { HomePageViewModel(get(), get()) }

}