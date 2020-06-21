package com.example.hometeach.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.hometeach.fragments.OngoingFragment

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                OngoingFragment()
            }
            1 -> OngoingFragment()
            else -> {
                return OngoingFragment();
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Request"
            1 -> "Ongoing"
            else -> {
                return "Request"
            }
        }
    }
}