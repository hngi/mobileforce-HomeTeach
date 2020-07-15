package com.mobileforce.hometeach.ui.tutordashboardfragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.OnItemtouch
import com.mobileforce.hometeach.adapters.TutorClassesAdapter
import com.mobileforce.hometeach.databinding.FragmentHomePageTutorBinding
import com.mobileforce.hometeach.models.TutorClassesDataModel
import kotlinx.android.synthetic.main.fragment_home_page_tutor.*
import java.util.*

class HomePageTutorFragment : Fragment(), OnItemtouch {
    private lateinit var tutor_classes_list: List<TutorClassesDataModel>
    lateinit var navController: NavController
    private lateinit var binding: FragmentHomePageTutorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding =  FragmentHomePageTutorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val btnSignOut =  binding.signout
        val username = binding.username
        var calendar = binding.calendar
        val modifyBtn = binding.modifyBtn
        val cardDetail = binding.cardDetails
        val withdrawal = binding.withdrawal
        val myBanks = binding.mybanks
        val tutorClassesRecycler = binding.tutorClasses

        // the two variable below are for testing purposes only
        var testurl = "https://twitter.com/markessien/status/1276935597895098368/photo/1"
        var testurl1 ="https://twitter.com/mikailkyusuf/photo"
        tutor_classes_list = listOf(
            TutorClassesDataModel(testurl, "Mike Pence", "Biology", "Grade 5", 20, 60),
            TutorClassesDataModel(testurl1, "Mayokun Bello", "Mathematics", "Grade 3", 60, 54)
        )
        val adapter = TutorClassesAdapter(tutor_classes_list, this)

        tutorClassesRecycler.adapter = adapter

        tutorClassesRecycler.apply {
            layoutManager =
                LinearLayoutManager(
                    activity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                ) as RecyclerView.LayoutManager?
        }
        tutorClassesRecycler.setHasFixedSize(true)

        modifyBtn.setOnClickListener {
            date_picker()
        }
        withdrawal.setOnClickListener {

            navController.navigate(R.id.makeWithdrawalFragment)
        }

        cardDetail.setOnClickListener {
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