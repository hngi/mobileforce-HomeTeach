package com.mobileforce.hometeach.ui.tutorlist


import android.annotation.SuppressLint
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FancyDateDialogBinding
import com.mobileforce.hometeach.databinding.SuccessRequestLayoutBinding


/**
 * Created by Mayokun Adeniyi on 15/07/2020.
 */
class SelectDateDialog : DialogFragment() {

    @SuppressLint("ClickableViewAccessibility")
    fun showPopupWindow(view: View, fragment: TutorListFragment) {

        val inflater = view.context
            .getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.fancy_date_dialog, null)
        val binding = FancyDateDialogBinding.bind(popupView)

        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        val focusable = true

        val popupWindow = PopupWindow(popupView, width, height, focusable)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)


        binding.apply {
            fromHourPicker.maxValue = 23
            fromMinutePicker.maxValue = 59
            toHourPicker.maxValue = 23
            toMinutePicker.maxValue = 59
        }



        binding.cancelTextview.setOnClickListener {
            popupWindow.dismiss()
        }
        binding.clearTextview.setOnClickListener {
            binding.calendarView.clearSelection()
            binding.apply {
                fromHourPicker.value = 0
                fromMinutePicker.value = 0
                toHourPicker.value = 0
                toMinutePicker.value = 0
            }
        }

        binding.approveTextview.setOnClickListener {
            val selectedDates = binding.calendarView.selectedDates.map { calendarDay ->
                "${calendarDay.year}".plus("-").plus("${calendarDay.month}").plus("-").plus("${calendarDay.day}")
            }
            println(selectedDates)
            val dialogData = DialogData(
                binding.fromHourPicker.value.toString(),
                binding.fromMinutePicker.value.toString(),
                binding.toHourPicker.value.toString(),
                binding.toMinutePicker.value.toString(),
                selectedDates
            )
            val dialogListener = fragment as SelectDateListener
            dialogListener.onApproveClicked(dialogData)
            popupWindow.dismiss()
        }
    }

    fun showSuccessDialog(view: View){

        val inflater = view.context
            .getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.success_request_layout, null)

        val binding = SuccessRequestLayoutBinding.bind(popupView)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        val focusable = true

        val popupWindow = PopupWindow(popupView, width, height, focusable)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        binding.btn.setOnClickListener {
            popupWindow.dismiss()
        }
    }

    interface SelectDateListener {
        fun onApproveClicked(dialogData: DialogData)
    }

}

data class DialogData(
    val fromHour: String,
    val fromMinute: String,
    val toHour: String,
    val toMinute: String,
    val dates: List<String>
)

