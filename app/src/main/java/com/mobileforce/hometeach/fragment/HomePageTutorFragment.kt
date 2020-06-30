package com.mobileforce.hometeach.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.TextView
import com.mobileforce.hometeach.R
import java.util.*

class HomePageTutorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page_tutor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_sign_out = view.findViewById<Button>(R.id.signout)
        val username = view.findViewById<TextView>(R.id.username)
        var calendar = view.findViewById<CalendarView>(R.id.calendar)
        val modify_btn = view.findViewById<Button>(R.id.modify_btn)
        val card_detail = view.findViewById<LinearLayout>(R.id.card_details)
        val withdrawal = view.findViewById<LinearLayout>(R.id.withdrawal)
        val myBanks = view.findViewById<LinearLayout>(R.id.mybanks)


    }


}