package com.example.hometeach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomNavigationView.setOnNavigationItemReselectedListener(mOnnavigationitemselected)


    }



    private val mOnnavigationitemselected =
        BottomNavigationView.OnNavigationItemReselectedListener {


            when (it.itemId) {

                R.id.books -> {

                    return@OnNavigationItemReselectedListener
                }
                R.id.contacts -> {

                    return@OnNavigationItemReselectedListener
                }
                R.id.home -> {
                    val fragment = ParentStudentHomepageFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment, fragment.javaClass.simpleName)
                        .commit()

                    return@OnNavigationItemReselectedListener
                }
                R.id.notification -> {

                    return@OnNavigationItemReselectedListener
                }
                R.id.profile -> {

                    return@OnNavigationItemReselectedListener
                }

            }
        }

}