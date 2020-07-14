package com.mobileforce.hometeach.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.AppConstants
import com.mobileforce.hometeach.AppConstants.USER_TUTOR
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentProfileBinding
import com.mobileforce.hometeach.databinding.FragmentStudentProfileBinding
import com.mobileforce.hometeach.localsource.PreferenceHelper
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.remotesource.wrappers.TutorDetailsResponse
import com.mobileforce.hometeach.ui.signin.LoginActivity
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.snack
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Authored by enyason
 */
class ProfileFragment : Fragment() {
    private lateinit var bindingTutor: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var bindingStudent: FragmentStudentProfileBinding
    private val pref: PreferenceHelper by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (pref.userType == USER_TUTOR){
            bindingTutor = FragmentProfileBinding.inflate(layoutInflater)
            bindingTutor.root
        }else{
            bindingStudent = FragmentStudentProfileBinding.inflate(layoutInflater)
            bindingStudent.root
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (pref.userType == USER_TUTOR) {
            setUpProfileForTutor()

        }else{

        }


    }
    private fun setUpProfileForTutor(){
        viewModel.getTutorDetails()
        viewModel.getTutorDetails.observe(viewLifecycleOwner, Observer { result ->
            Log.d("Result", result.toString())
            when (result) {
                Result.Loading -> {

                }
                is Result.Success -> {
                    bindingTutor.tutorName.text = result.data?.full_name
                    bindingTutor.tutorNameProfile.text= result.data?.full_name
                }

                is Result.Error -> {

                    bindingTutor.profileLayout.snack(message = result.exception.localizedMessage)
                }
            }

        })
        bindingTutor.viewAll.setOnClickListener {
            val mDialog = CredentialDialog.newInstance()
            mDialog.show(requireActivity().supportFragmentManager, "credentials")

        }
    }

}