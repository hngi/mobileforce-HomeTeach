package com.mobileforce.hometeach.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //start OnBoarding activity
        val intent = Intent(this@SplashActivity, ExploreActivity::class.java)
        startActivity(intent)
        //finish this activity
        finish()

    }
}