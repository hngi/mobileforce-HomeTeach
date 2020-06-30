package com.mobileforce.hometeach.TutorDashBoardFragments

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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.OnItemtouch
import com.mobileforce.hometeach.adapters.TutorClassesAdapter
import com.mobileforce.hometeach.models.TutorClassesDataModel
import kotlinx.android.synthetic.main.fragment_home_page_tutor.*
import java.util.*

class HomePageTutorFragment : Fragment(), OnItemtouch {
    private lateinit var tutor_classes_list: List<TutorClassesDataModel>
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page_tutor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val btn_sign_out = view.findViewById<Button>(R.id.signout)
        val username = view.findViewById<TextView>(R.id.username)
        var calendar = view.findViewById<CalendarView>(R.id.calendar)
        val modify_btn = view.findViewById<Button>(R.id.modify_btn)
        val card_detail = view.findViewById<LinearLayout>(R.id.card_details)
        val withdrawal = view.findViewById<LinearLayout>(R.id.withdrawal)
        val myBanks = view.findViewById<LinearLayout>(R.id.mybanks)
        val tutor_classes_recycler = view.findViewById<RecyclerView>(R.id.tutor_classes)

        // the two variable below are for testing purposes only
        var testurl = "https://twitter.com/markessien/status/1276935597895098368/photo/1"
        var testurl1 ="https://twitter.com/mikailkyusuf/photo"
        tutor_classes_list = listOf(
            TutorClassesDataModel(testurl, "Mike Pence", "Biology", "Grade 5", 20, 60),
            TutorClassesDataModel(testurl1, "Mayokun Bello", "Mathematics", "Grade 3", 60, 54)
        )
        val adapter = TutorClassesAdapter(tutor_classes_list, this)

        tutor_classes_recycler.adapter = adapter

        tutor_classes_recycler.apply {
            layoutManager =
                LinearLayoutManager(
                    activity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                ) as RecyclerView.LayoutManager?
        }
        tutor_classes_recycler.setHasFixedSize(true)

        modify_btn.setOnClickListener {
            date_picker()
        }
        withdrawal.setOnClickListener {

            navController.navigate(R.id.makeWithdrawalFragment)
        }

        card_detail.setOnClickListener {
            navController.navigate(R.id.cardDetails)
        }
        mybanks.setOnClickListener {

            navController.navigate(R.id.myBanks)
        }

    }

    fun date_picker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = activity?.let {
            DatePickerDialog(
                it,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

//                    MONTHS[monthOfYear]
//                   dayOfMonth
                    //MONTHS[monthOfYear]

                },
                year,
                month,
                day
            )
        }

        if (dpd != null) {
            dpd.show()
        }
    }

    override fun OnUserclicked(datamodel: TutorClassesDataModel, position: Int) {
        //OPEN INTENT  OR FRAGMENT TO EDIT THE TUTORS CLASSES
    }

}