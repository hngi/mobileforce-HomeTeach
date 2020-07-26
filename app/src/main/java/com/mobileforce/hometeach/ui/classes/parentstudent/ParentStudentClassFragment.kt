package com.mobileforce.hometeach.ui.classes.parentstudent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mobileforce.hometeach.databinding.FragmentParentStudentClassBinding
import com.mobileforce.hometeach.ui.classes.adapters.viewpageradapters.ParentViewPagerFragmentAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [ParentStudentClassFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ParentStudentClassFragment : Fragment() {
    private lateinit var binding: FragmentParentStudentClassBinding
    private lateinit var parentViewPagerAdapter: ParentViewPagerFragmentAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParentStudentClassBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //parentViewPagerAdapter = ParentViewPagerFragmentAdapter(this)
//        viewPager = binding.parentViewpager
//        viewPager.adapter = parentViewPagerAdapter
//        val tabLayout = binding.parentTabLayout
//        TabLayoutMediator(tabLayout,viewPager){ tab, position ->
//            tab.text = titles[position]
//        }.attach()
//        binding.toolBar.setNavigationOnClickListener {
//            findNavController().popBackStack()
//        }
    }

    companion object{
        private val titles = arrayOf("Requests","Ongoing")
    }
}