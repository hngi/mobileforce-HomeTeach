package com.mobileforce.hometeach.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FragmentTutorDetailsBinding
import kotlinx.android.synthetic.main.request_layout.view.*


class TutorDetailsFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0
    lateinit var navController: NavController
    private lateinit var binding:FragmentTutorDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.requestService.setOnClickListener {
            val calendar: Calendar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Calendar.getInstance()
            } else {
                TODO("VERSION.SDK_INT < N")
            }
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                activity?.let { it1 -> DatePickerDialog(it1, this, year, month,day) }
            datePickerDialog?.show()

        }

        binding.viewAll.setOnClickListener {
            val mDialogView = LayoutInflater.from(activity).inflate(R.layout.credentials_dialog, null)
            val mBuilder = activity?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)

            }

            val  mAlertDialog = mBuilder?.show()
          mBuilder?.setTitle("Credentials")
            mDialogView.btn.setOnClickListener {
                mAlertDialog?.hide()
            }
        }


    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = day
        myYear = year
        myMonth = month
        val calendar: Calendar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Calendar.getInstance()
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(activity, this, hour, minute,
            DateFormat.is24HourFormat(activity))
        timePickerDialog.show()
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
       val date = "" +myDay +" / "+ myMonth +" / "+ myYear + "\n"+"\t\t\t"+ myHour + ":" + "" + myMinute
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.request_layout, null)
        val mBuilder = activity?.let { it1 ->
            AlertDialog.Builder(it1)
                .setView(mDialogView)
        }

        val  mAlertDialog = mBuilder?.show()
        mDialogView.date.text = date
        mDialogView.btn.setOnClickListener {

            mAlertDialog?.hide()
        }

    }

}