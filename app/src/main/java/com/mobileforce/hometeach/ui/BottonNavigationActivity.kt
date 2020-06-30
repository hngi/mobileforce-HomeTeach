package com.mobileforce.hometeach.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.BottonNavLayoutBinding

//import kotlinx.android.synthetic.main.activity_main.*

class BottonNavigationActivity : AppCompatActivity() {;

    lateinit var binding: BottonNavLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottonNavLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(this, R.id.nav_host_fragment)

        binding.bottomNavigationView.setupWithNavController(navController)


    }
}