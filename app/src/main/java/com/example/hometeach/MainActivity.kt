package com.example.hometeach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hometeach.explore.ExploreActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToLoginActivity(view: View) {
        var loginActivityIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginActivityIntent)
    }
}