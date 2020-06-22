package com.mobileforce.hometeach.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobileforce.hometeach.adapters.OnBoardAdapter
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.models.OnBoardItem

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var pagerIndicator: LinearLayout
    private var dotCount: Int = 0
    private lateinit var dots: ArrayList<ImageView>
    private lateinit var onBoardPager: ViewPager
    private lateinit var skipText: TextView
    private lateinit var adapter: OnBoardAdapter
    private var onBoardItems: ArrayList<OnBoardItem> = ArrayList()
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        onBoardPager = findViewById(R.id.view_pager)
        pagerIndicator = findViewById(R.id.view_pager_counter_layout)
        skipText = findViewById(R.id.tv_skip)
        fab = findViewById(R.id.on_boarding_fab)

        loadData()
        adapter =
            OnBoardAdapter(this, onBoardItems)
        onBoardPager.adapter = adapter
        onBoardPager.currentItem = 0
        onBoardPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until dotCount){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this@OnBoardingActivity,
                        R.drawable.non_selected_item_dot
                    ))
                    dots[position].setImageDrawable(ContextCompat.getDrawable(this@OnBoardingActivity,
                        R.drawable.selected_item_dot
                    ))
                    if (position == 2){
                        fab.visibility = View.VISIBLE
                        skipText.visibility = View.INVISIBLE
                    } else {
                        fab.visibility = View.INVISIBLE
                        skipText.visibility = View.VISIBLE
                    }
                }
            }
        })

        skipText.setOnClickListener {
            startActivity(Intent(this, ExploreActivity::class.java))
            //finish this activity
            finish()
        }

        fab.setOnClickListener {
            startActivity(Intent(this, ExploreActivity::class.java))
            //finish this activity
            finish()
        }
        setPageViewController()
    }

    private fun loadData(){
        val desc = listOf(
            R.string.on_boarding_text_1,
            R.string.on_boarding_text_2,
            R.string.on_boarding_text_3
        )
        val imageId = listOf(
            R.drawable.onboarding_avatar_1,
            R.drawable.onboarding_avatar_2,
            R.drawable.onboarding_avatar_3
        )

        for (i in imageId.indices){
            val item = OnBoardItem()
            item.imageID = imageId[i]
            item.description = resources.getString(desc[i])
            onBoardItems.add(item)
        }
    }

    private fun setPageViewController(){
        dotCount = adapter.count
        dots = ArrayList(dotCount)
        for (i in 0 until dotCount){
            dots.add(i, ImageView(this))
            dots[i].setImageDrawable(ContextCompat.getDrawable(this,
                R.drawable.non_selected_item_dot
            ))
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(6, 0, 6, 0)
            pagerIndicator.addView(dots[i], params)
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(this,
            R.drawable.selected_item_dot
        ))
    }
}