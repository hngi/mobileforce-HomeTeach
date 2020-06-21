package com.example.hometeach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hometeach.explore.ExploreActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}