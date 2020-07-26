package com.mobileforce.hometeach.ui.classes.tutor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.CircleTransform
import com.mobileforce.hometeach.databinding.FragmentProfileBinding
import com.mobileforce.hometeach.databinding.FragmentStudentDetailsBinding
import com.squareup.picasso.Picasso


class StudentDetails : Fragment() {

    lateinit var navController: NavController
    lateinit var binding: FragmentStudentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUrl = arguments?.getString("student_pic")
        Picasso.get().load(imageUrl).error(R.drawable.profile_image).into(binding.studentPic)

        binding.studentGrade.text = arguments?.getString("student_garde")
            binding.studentName.text = arguments?.getString("student_name")

    }
}