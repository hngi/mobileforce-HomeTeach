package com.mobileforce.hometeach.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.mobileforce.hometeach.R

/**
 * Authored by enyason
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editBtn = view.findViewById<MaterialButton>(R.id.edit_button)
        editBtn.setOnClickListener {
            val trans = parentFragmentManager.beginTransaction()
            trans.add(R.id.nav_host_fragment, EditTutorProfileFragment.newInstance()).commit()
        }

        // Displays the Credentials DialogFragment
        val viewAll = view.findViewById<TextView>(R.id.view_all)
        viewAll.setOnClickListener {
            val credentialDialog = CredentialDialog()
            val trans = parentFragmentManager.beginTransaction()
            credentialDialog.show(trans, "dialog")
        }
}


}