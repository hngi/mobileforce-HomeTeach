package com.mobileforce.hometeach.ui.classes.tutor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mobileforce.hometeach.databinding.FragmentTutorClassBinding
import com.mobileforce.hometeach.ui.classes.adapters.viewpageradapters.TutorViewPagerFragmentAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [TutorClassFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TutorClassFragment : Fragment() {
    private lateinit var binding: FragmentTutorClassBinding
    private lateinit var tutorViewPagerAdapter: TutorViewPagerFragmentAdapter
    private lateinit var tutorViewPager: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding = FragmentTutorClassBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        tutorViewPagerAdapter = TutorViewPagerFragmentAdapter(this)
//        tutorViewPager = binding.tutorViewpager
//        tutorViewPager.adapter = tutorViewPagerAdapter
//        val tabLayout = binding.tutorTabLayout
//        TabLayoutMediator(tabLayout, tutorViewPager) { tab, position ->
//            tab.text = titles[position]
//        }.attach()
    }

    companion object {
        private val titles = arrayOf("Requests", "Upcoming")
    }
}