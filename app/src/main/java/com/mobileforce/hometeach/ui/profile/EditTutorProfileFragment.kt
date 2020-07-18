package com.mobileforce.hometeach.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.mobileforce.hometeach.R
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.fragment_edit_tutor_profile.*

/**
 * Authored by MayorJay and domn
 */

class EditTutorProfileFragment : Fragment() {

    companion object {
        fun newInstance() = EditTutorProfileFragment()
    }

    private val listener by lazy {
        object: MaterialSpinner.OnItemSelectedListener {
            override fun onItemSelected(
                parent: MaterialSpinner,
                view: View?,
                position: Int,
                id: Long
            ) {
                parent.focusSearch(View.FOCUS_UP)?.requestFocus()
            }

            override fun onNothingSelected(parent: MaterialSpinner) {
                //("Not yet implemented")
            }
        }
    }

    //private val viewModel: EditTutorProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_tutor_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflates the Spinner displaying the tutor's field
        ArrayAdapter.createFromResource(requireContext(), R.array.fields_array, android.R.layout.simple_spinner_item).let {
            it.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
            sp_select_field.apply {
                adapter = it
                onItemSelectedListener = listener
            }
        }

        // Inflates the Spinner displaying the states of origin
        ArrayAdapter.createFromResource(requireContext(), R.array.origin_array, android.R.layout.simple_spinner_item).let {
            it.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
            sp_select_origin.apply {
                adapter = it
                onItemSelectedListener = listener
            }
        }

        // Displays the Credentials DialogFragment
        tv_view_all.setOnClickListener {
            val credentialDialog = CredentialDialog()
            val trans = parentFragmentManager.beginTransaction()
            credentialDialog.show(trans, "dialog")
        }

//        val profileList = viewModel.getProfileList()
//        val profileResponse = profileList[0]
//        var currentUserId: Int = 0
//        var currentUserEmail: String = ""
//        if (profileResponse.address == et_address_input.text.toString()){
//            currentUserId = profileResponse.id
//            currentUserEmail = profileResponse.email
//        }

        bt_save_profile.setOnClickListener {
//            val profileData = Params.EditTutorProfile(
//                email = currentUserEmail,
//                full_name = et_name_input.text.toString(),
//                desc = et_description.text.toString(),
//                field = sp_select_field.selectedItem.toString(),
//                major_course = et_course_input.text.toString(),
//                other_courses = et_other_course_input.text.toString(),
//                state = sp_select_origin.selectedItem.toString(),
//                address = et_address_input.text.toString()
//            )
//            viewModel.editTutorProfile(currentUserId, profileData)
        }
    }

}