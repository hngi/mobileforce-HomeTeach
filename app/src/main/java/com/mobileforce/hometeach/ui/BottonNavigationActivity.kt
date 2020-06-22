package com.mobileforce.hometeach.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mobileforce.hometeach.fragment.ParentStudentHomepageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobileforce.hometeach.R

//import kotlinx.android.synthetic.main.activity_main.*

class BottonNavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.botton_nav_layout)


        //  bottomNavigationView.setOnNavigationItemReselectedListener(mOnnavigationitemselected)
        val bottomNavigationView= findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.books -> {
                  //call the load fragment method and passs the name of the fragment

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.contacts -> {
                    //call the load fragment method and passs the name of the fragment
                    return@setOnNavigationItemSelectedListener true
                }


                R.id.home -> {
                    loadFragment(ParentStudentHomepageFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.notification -> {
                    //call the load fragment method and passs the name of the fragment
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    //call the load fragment method and passs the name of the fragment
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false
        }

        }

//        bottomNavigationView.setOnNavigationItemSelectedListener {
//
//            when (it.itemId) {
//
//                R.id.books -> {
//                  //call the load fragment method and passs the name of the fragment
//
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.contacts -> {
//                    //call the load fragment method and passs the name of the fragment
//                    return@setOnNavigationItemSelectedListener true
//                }
//
//
//                R.id.home -> {
//                    loadFragment(ParentStudentHomepageFragment())
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.notification -> {
//                    //call the load fragment method and passs the name of the fragment
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.profile -> {
//                    //call the load fragment method and passs the name of the fragment
//                    return@setOnNavigationItemSelectedListener true
//                }
//
//            }
//            false
//        }
//
//
//    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}