package com.mobileforce.hometeach.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobileforce.hometeach.utils.AppConstants.USER_STUDENT
import com.mobileforce.hometeach.utils.AppConstants.USER_TUTOR
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.utils.PreferenceHelper
import com.mobileforce.hometeach.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_explore.*
import org.koin.android.ext.android.inject


class ExploreActivity : AppCompatActivity() {

    private val pref: PreferenceHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        val isFirstRun =
            getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getBoolean("isFirstRun", true)

//        val isLoggedIn = PreferenceHelper(this).isLoggedIn
//        if (isLoggedIn){
//            startActivity(Intent(this@ExploreActivity, BottonNavigationActivity::class.java))
//            finish()
//        }

        if (isFirstRun) {
            //show OnBoarding activity
            startActivity(Intent(this@ExploreActivity, OnBoardingActivity::class.java))
            finish()
        }
        getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
            .putBoolean("isFirstRun", false).apply()

        tutorButton.setOnClickListener {
            //save user type to shared preference
            pref.userType = USER_TUTOR
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        studentButton.setOnClickListener {
            //save user type to shared preference
            pref.userType = USER_STUDENT
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }
}