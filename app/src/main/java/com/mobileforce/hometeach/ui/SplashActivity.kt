package com.mobileforce.hometeach.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import com.mobileforce.hometeach.R
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        //hiding title bar of this activity
        window.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        Timer().schedule(object: TimerTask(){
            override fun run() {
                //start main activity
                val intent = Intent(this@SplashActivity, ExploreActivity::class.java)
                startActivity(intent)
                //finish this activity
                finish()
            }

        }, 4000L)


    }
}