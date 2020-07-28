package com.mobileforce.hometeach.ui.classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mobileforce.hometeach.databinding.FragmentParentStudentClassBinding
import com.mobileforce.hometeach.databinding.FragmentTutorClassBinding
import com.mobileforce.hometeach.ui.classes.adapters.viewpageradapters.ParentViewPagerFragmentAdapter
import com.mobileforce.hometeach.ui.classes.adapters.viewpageradapters.TutorViewPagerFragmentAdapter
import com.mobileforce.hometeach.utils.AppConstants
import com.mobileforce.hometeach.utils.AppConstants.USER_STUDENT
import com.mobileforce.hometeach.utils.PreferenceHelper
import org.koin.android.ext.android.inject

/**
 * Created by MayorJay
 */
class ClassesFragment : Fragment() {

    private lateinit var bindingStudent: FragmentParentStudentClassBinding
    private lateinit var bindingTutor: FragmentTutorClassBinding
    private lateinit var parentViewPagerAdapter: ParentViewPagerFragmentAdapter
    private lateinit var tutorViewPagerAdapter: TutorViewPagerFragmentAdapter
    private lateinit var studentViewPager: ViewPager2
    private lateinit var tutorViewPager: ViewPager2
    private val pref: PreferenceHelper by inject()
    private lateinit var navController: NavController

    companion object {
        private val titles = arrayOf("Requests", "Upcoming")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (pref.userType == USER_STUDENT) {
            bindingStudent = FragmentParentStudentClassBinding.inflate(layoutInflater)
            bindingStudent.root
        } else {
            bindingTutor = FragmentTutorClassBinding.inflate(layoutInflater)
            bindingTutor.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        if (pref.userType == AppConstants.USER_TUTOR) {
            setUpClassPageForTutor()
        } else {
            setUpClassPageForStudent()
        }
    }

    private fun setUpClassPageForTutor() {
        tutorViewPagerAdapter = TutorViewPagerFragmentAdapter(this)
        tutorViewPager = bindingTutor.tutorViewpager
        tutorViewPager.adapter = tutorViewPagerAdapter
        val tabLayout = bindingTutor.tutorTabLayout
        TabLayoutMediator(tabLayout, tutorViewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
        bindingTutor.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpClassPageForStudent() {
        parentViewPagerAdapter = ParentViewPagerFragmentAdapter(this)
        studentViewPager = bindingStudent.parentViewpager
        studentViewPager.adapter = parentViewPagerAdapter
        val tabLayout = bindingStudent.parentTabLayout
        TabLayoutMediator(tabLayout, studentViewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
        bindingStudent.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}