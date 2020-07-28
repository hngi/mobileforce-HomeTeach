package com.mobileforce.hometeach.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileforce.hometeach.R

class EditStudentProfileFragment : Fragment() {

    companion object {
        fun newInstance() = EditStudentProfileFragment()
    }

    private lateinit var viewModel: EditStudentProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_student_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditStudentProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}