package com.mobileforce.hometeach.ui.classes.adapters.viewpageradapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobileforce.hometeach.ui.classes.ClassesFragment
import com.mobileforce.hometeach.ui.classes.parentstudent.ParentUpcomingFragment
import com.mobileforce.hometeach.ui.classes.parentstudent.ParentRequestFragment

/**
 * Created by Mayokun Adeniyi on 27/06/2020.
 */

class ParentViewPagerFragmentAdapter(fragment: ClassesFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ParentRequestFragment()
            }
            1 -> {
                ParentUpcomingFragment()
            }
            else -> ParentRequestFragment()
        }
    }
}