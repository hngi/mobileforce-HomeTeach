package com.mobileforce.hometeach.di

import com.mobileforce.hometeach.local.PreferenceHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val appModule = module {

    single { PreferenceHelper(androidContext()) }
}