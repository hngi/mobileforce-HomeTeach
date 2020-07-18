package com.mobileforce.hometeach.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobileforce.hometeach.ui.signin.LoginActivity
import com.mobileforce.hometeach.utils.PreferenceHelper
import org.koin.android.ext.android.inject


class SplashActivity : AppCompatActivity() {

    private val pref: PreferenceHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (pref.isFristRun) {
            //show OnBoarding activity
            startActivity(Intent(this, OnBoardingActivity::class.java))
            pref.isFristRun = false
        } else {

            if (pref.isLoggedIn) {
                startActivity(Intent(this, BottomNavigationActivity::class.java))

            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }

        finish()

    }


}