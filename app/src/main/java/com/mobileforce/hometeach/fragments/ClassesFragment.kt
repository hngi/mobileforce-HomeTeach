package com.mobileforce.hometeach.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.MyPagerAdapter
import com.google.android.material.tabs.TabLayout

class ClassesFragment : Fragment() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout
    companion object {
        fun newInstance() = ClassesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.classes_fragment, container, false)
        viewPager = view.findViewById(R.id.viewpager)
        tabs = view.findViewById(R.id.sliding_tabs)

        val fragmentAdapter = MyPagerAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPager)

        return view
    }
}