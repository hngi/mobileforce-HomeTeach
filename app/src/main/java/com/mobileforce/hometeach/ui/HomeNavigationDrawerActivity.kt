package com.mobileforce.hometeach.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.BottonNavLayoutBinding
import com.mobileforce.hometeach.utils.makeGone
import com.mobileforce.hometeach.utils.makeVisible


class HomeNavigationDrawerActivity : AppCompatActivity() {

    lateinit var binding: BottonNavLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottonNavLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(this, R.id.nav_host_fragment)

        binding.bottomNavigationView.setupWithNavController(navController)


    }

    fun hideBottomBar() {
        binding.materialCardView.makeGone()
    }

    fun makeBottomBarVisible() {
        binding.materialCardView.makeVisible()

    }
}