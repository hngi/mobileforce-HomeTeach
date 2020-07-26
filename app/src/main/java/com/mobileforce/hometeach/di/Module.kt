package com.mobileforce.hometeach.di

import androidx.room.Room
import com.mobileforce.hometeach.data.TutorRepositoryImpl
import com.mobileforce.hometeach.data.UserRepositoryImpl
import com.mobileforce.hometeach.data.repository.TutorRepository
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.DataSourceFactory
import com.mobileforce.hometeach.data.sources.LocalDataSource
import com.mobileforce.hometeach.data.sources.RemoteDataSource
import com.mobileforce.hometeach.data.sources.local.AppDataBase
import com.mobileforce.hometeach.data.sources.remote.Api
import com.mobileforce.hometeach.ui.classes.tutor.StudentRequestViewModel
import com.mobileforce.hometeach.ui.classes.tutor.TutorUpcomingViewModel
import com.mobileforce.hometeach.ui.classes.tutor.TutorRequestViewModel
import com.mobileforce.hometeach.ui.booktutor.BookTutorViewModel
import com.mobileforce.hometeach.ui.home.HomePageViewModel
import com.mobileforce.hometeach.ui.message.ChatViewModel
import com.mobileforce.hometeach.ui.profile.EditTutorViewModel
import com.mobileforce.hometeach.ui.profile.ProfileViewModel
import com.mobileforce.hometeach.ui.signin.SignInViewModel
import com.mobileforce.hometeach.ui.signup.SignUpViewModel
import com.mobileforce.hometeach.ui.studentpayments.carddetails.StudentAddCardDetailsViewModel
import com.mobileforce.hometeach.ui.studentpayments.carddetails.StudentCardDetailsViewModel
import com.mobileforce.hometeach.ui.studentpayments.makepayments.StudentMakePaymentViewModel
import com.mobileforce.hometeach.ui.tutordetails.TutorDetailsViewModel
import com.mobileforce.hometeach.ui.tutorlist.TutorListViewModel
import com.mobileforce.hometeach.ui.withdrawalscreens.mybank.MyBankViewModel
import com.mobileforce.hometeach.ui.withdrawalscreens.withdraw.WithDrawalViewModel
import com.mobileforce.hometeach.utils.AppConstants.BASE_URL
import com.mobileforce.hometeach.utils.AppConstants.DATABASE_NAME
import com.mobileforce.hometeach.utils.PreferenceHelper
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
            .connectTimeout(120, TimeUnit.SECONDS) //Backend is really slow
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
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
    single<TutorRepository> { TutorRepositoryImpl(get()) }


    //<---------------------view models -------------------------------------->//

    factory { SignInViewModel(get(), get()) }
    factory { SignUpViewModel(get()) }
    factory { HomePageViewModel(get(), get()) }
    factory { TutorListViewModel(get()) }
    factory { ProfileViewModel(get()) }

    factory { BookTutorViewModel(get()) }
    factory { TutorDetailsViewModel(get()) }

    factory { EditTutorViewModel(get(), get()) }
    factory { StudentAddCardDetailsViewModel(get()) }
    factory { StudentCardDetailsViewModel(get()) }
    factory { StudentMakePaymentViewModel(get()) }

    factory { ChatViewModel(get()) }
    factory { TutorRequestViewModel(get()) }
    factory { TutorUpcomingViewModel(get()) }
    factory {  StudentRequestViewModel(get()) }


    factory { WithDrawalViewModel(get()) }
    factory { MyBankViewModel(get()) }
}