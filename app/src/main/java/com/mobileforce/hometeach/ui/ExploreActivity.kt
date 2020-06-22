package com.mobileforce.hometeach.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobileforce.hometeach.R


class ExploreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        val isFirstRun =
            getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getBoolean("isFirstRun", true)

        if (isFirstRun) {
            //show start activity
            startActivity(Intent(this@ExploreActivity, OnBoardingActivity::class.java))
        }
        getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
            .putBoolean("isFirstRun", false).apply()
    }
}