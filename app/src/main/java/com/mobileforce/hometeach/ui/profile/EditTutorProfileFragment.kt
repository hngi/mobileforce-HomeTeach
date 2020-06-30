package com.mobileforce.hometeach.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.mobileforce.hometeach.R
import com.tiper.MaterialSpinner

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
                TODO("Not yet implemented")
            }
        }
    }

    private lateinit var fieldMaterialSpinner: MaterialSpinner
    private lateinit var originMaterialSpinner: MaterialSpinner
    private lateinit var viewModel: EditTutorProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_tutor_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditTutorProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflates the Spinner displaying the tutor's field
        fieldMaterialSpinner = view.findViewById(R.id.sp_select_field)
        ArrayAdapter.createFromResource(requireContext(), R.array.fields_array, android.R.layout.simple_spinner_item).let {
            it.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
            fieldMaterialSpinner.apply {
                adapter = it
                onItemSelectedListener = listener
            }
        }

        // Inflates the Spinner displaying the states of origin
        originMaterialSpinner = view.findViewById(R.id.sp_select_origin)
        ArrayAdapter.createFromResource(requireContext(), R.array.origin_array, android.R.layout.simple_spinner_item).let {
            it.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
            originMaterialSpinner.apply {
                adapter = it
                onItemSelectedListener = listener
            }
        }

        // Displays the Credentials DialogFragment
        val viewAllCred = view.findViewById<TextView>(R.id.tv_view_all)
        viewAllCred?.setOnClickListener {
            val credentialDialog = CredentialDialog()
            val trans = parentFragmentManager.beginTransaction()
            credentialDialog.show(trans, "dialog")
        }
    }

}