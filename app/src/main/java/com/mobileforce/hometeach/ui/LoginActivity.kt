package com.mobileforce.hometeach.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signIn.setOnClickListener {
            navigateToDashBoard()
        }

    }

    private fun navigateToDashBoard() {
        startActivity(Intent(this, BottonNavigationActivity::class.java))
    }
}