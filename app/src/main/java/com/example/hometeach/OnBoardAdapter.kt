package com.example.hometeach

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter

class OnBoardAdapter(private val context: Context, onBoardItems: ArrayList<OnBoardItem>) : PagerAdapter() {

    private var onBoardItems: ArrayList<OnBoardItem> = ArrayList()

    init {
        this.onBoardItems = onBoardItems
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return onBoardItems.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(context).inflate(R.layout.onboard_item, container, false)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val textView = itemView.findViewById<TextView>(R.id.textView)
        val item = onBoardItems[position]
        imageView.setImageResource(item.imageID)
        textView.text = item.description
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}