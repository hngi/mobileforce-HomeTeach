package com.mobileforce.hometeach

import androidx.multidex.MultiDexApplication
import co.paystack.android.PaystackSdk
import com.mobileforce.hometeach.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : MultiDexApplication() {

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