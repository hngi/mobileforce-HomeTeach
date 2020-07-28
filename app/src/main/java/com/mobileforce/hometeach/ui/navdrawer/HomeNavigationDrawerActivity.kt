package com.mobileforce.hometeach.ui.navdrawer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.NavHeaderMainBinding
import com.mobileforce.hometeach.ui.signin.LoginActivity
import com.mobileforce.hometeach.utils.AppConstants.USER_STUDENT
import com.mobileforce.hometeach.utils.AppConstants.USER_TUTOR
import com.mobileforce.hometeach.utils.PreferenceHelper
import com.mobileforce.hometeach.utils.loadImage
import org.koin.android.ext.android.inject


class HomeNavigationDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHeaderMainBinding: NavHeaderMainBinding
    private val pref: PreferenceHelper by inject()
    private val viewModel by inject<NavDrawerViewModel>()
    private lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_navigation_drawer)
        val toolbar: Toolbar = findViewById(R.id.toolbarNav)
        setSupportActionBar(toolbar)
        navView = findViewById(R.id.nav_view)
        navHeaderMainBinding = NavHeaderMainBinding.inflate(layoutInflater)

        setupNavDrawer()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.classes, R.id.tutorHomePageFragment, R.id.profileFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    private fun setupNavDrawer() {
        when(pref.userType){
            USER_STUDENT -> {
                setupStudent()
            }

            USER_TUTOR -> {
                setupTutor()
            }
        }
    }

    private fun setupTutor() {
        viewModel.tutor.observe(this, Observer { tutor ->
            navHeaderMainBinding.fullName.text = tutor.user.fullName
            navHeaderMainBinding.emailNav.text = tutor.user.email
            navHeaderMainBinding.imageView.loadImage(tutor.profile_pic)
        })

    }

    private fun setupStudent() {
        viewModel.user.observe(this, Observer { user ->
//            navHeaderMainBinding.fullName.text = user.full_name
//            navHeaderMainBinding.emailNav.text = user.email
        })

        navView.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.logout -> {
                    Toast.makeText(this,"LOOOl",Toast.LENGTH_SHORT).show()
                    Log.i("FFFF","YES")
                    viewModel.logoutStudent()
                    startActivity(Intent(this, LoginActivity::class.java))
                    this.finish()
                }
            }
            true
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}