package com.mobileforce.hometeach.ui.classes.adapters.viewpageradapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobileforce.hometeach.ui.classes.ClassesFragment
import com.mobileforce.hometeach.ui.classes.tutor.TutorClassFragment
import com.mobileforce.hometeach.ui.classes.tutor.TutorUpcomingFragment
import com.mobileforce.hometeach.ui.classes.tutor.TutorRequestFragment

/**
 * Created by Mayokun Adeniyi on 28/06/2020.
 */

class TutorViewPagerFragmentAdapter(fragment: ClassesFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                TutorRequestFragment()
            }
            1 -> {
                TutorUpcomingFragment()
            }
            else -> TutorRequestFragment()
        }
    }
}