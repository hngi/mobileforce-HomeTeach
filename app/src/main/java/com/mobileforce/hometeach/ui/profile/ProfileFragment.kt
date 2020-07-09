package com.mobileforce.hometeach.ui.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentProfileBinding

/**
 * Authored by enyason
 */
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editButton.setOnClickListener {
            val trans = parentFragmentManager.beginTransaction()
            trans.add(R.id.nav_host_fragment, EditTutorProfileFragment.newInstance()).commit()
        }

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        // Displays the Credentials DialogFragment
        binding.viewAll.setOnClickListener {
            val credentialDialog = CredentialDialog()
            val trans = parentFragmentManager.beginTransaction()
            credentialDialog.show(trans, "dialog")
        }
    }


}