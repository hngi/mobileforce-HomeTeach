package com.mobileforce.hometeach

import android.app.Application
import co.paystack.android.PaystackSdk
import com.mobileforce.hometeach.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize PayStack
        PaystackSdk.initialize(applicationContext)

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
}