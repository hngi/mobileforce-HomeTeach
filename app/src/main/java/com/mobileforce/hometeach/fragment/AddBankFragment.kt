package com.mobileforce.hometeach.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toolbar
import com.mobileforce.hometeach.R


class AddBankFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(R.drawable.back_arrow)
        }
        val acc_name = view.findViewById<EditText>(R.id.acc_name)
        val acc_number = view.findViewById<EditText>(R.id.acc_number)
        val acc_route = view.findViewById<EditText>(R.id.acc_route)
        val ss_number = view.findViewById<EditText>(R.id.acc_security_number)
        val btn_save = view.findViewById<Button>(R.id.save)

    }
}