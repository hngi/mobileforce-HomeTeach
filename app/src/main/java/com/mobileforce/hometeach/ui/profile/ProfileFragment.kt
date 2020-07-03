package com.mobileforce.hometeach.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mobileforce.hometeach.R
import kotlinx.android.synthetic.main.fragment_profile.*

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

        // Just to help test the EditTutorProfile Fragment
        // Should be removed when not needed anymore
        val trans = parentFragmentManager.beginTransaction()

    }


}