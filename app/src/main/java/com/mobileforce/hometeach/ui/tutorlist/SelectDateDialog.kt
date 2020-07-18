package com.mobileforce.hometeach.ui.tutorlist


import android.annotation.SuppressLint
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.FancyDateDialogBinding
import com.mobileforce.hometeach.databinding.SuccessStudentRequestBinding
import com.prolificinteractive.materialcalendarview.CalendarDay


/**
 * Created by Mayokun Adeniyi on 15/07/2020.
 */
class SelectDateDialog : DialogFragment() {

    @SuppressLint("ClickableViewAccessibility", "NewApi")
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

        val viewGroup = view.parent as ViewGroup
        applyDim(viewGroup, 0.5f)



        binding.apply {
            fromHourPicker.maxValue = 23
            fromMinutePicker.maxValue = 59
            toHourPicker.maxValue = 23
            toMinutePicker.maxValue = 59
        }



        binding.cancelTextview.setOnClickListener {
            popupWindow.dismiss()
            clearDim(viewGroup)
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

        binding.calendarView.currentDate = CalendarDay.today()
        binding.calendarView.setOnDateChangedListener { _, date, selected ->
            if (date.isBefore(CalendarDay.today()) && selected){
                Toast.makeText(view.context,"You can't select a past date!",Toast.LENGTH_SHORT).show()
            }
        }

        var listIsClean: Boolean
        binding.approveTextview.setOnClickListener {
            listIsClean = true
            val selectedDates = binding.calendarView.selectedDates.map { calendarDay ->
                "${calendarDay.year}".plus("-").plus("${calendarDay.month}").plus("-")
                    .plus("${calendarDay.day}")
            }

            binding.calendarView.selectedDates.forEach {
                if (it.isBefore(CalendarDay.today())) {
                    Toast.makeText(view.context,"Some selected day(s) are in the past!",Toast.LENGTH_SHORT).show()
                    listIsClean = false
                }
            }

            if (selectedDates.isEmpty()) {
                Toast.makeText(view.context, "Select at least one day!", Toast.LENGTH_SHORT).show()
            } else if (listIsClean){
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
    }

    @SuppressLint("NewApi")
    fun showSuccessDialog(view: View, data: String?) {

        val inflater = view.context
            .getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.success_student_request, null)

        val binding = SuccessStudentRequestBinding.bind(popupView)
        binding.responseText.text = data
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT

        val focusable = true

        val popupWindow = PopupWindow(popupView, width, height, focusable)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        val viewGroup = view.parent as ViewGroup

        binding.btn.setOnClickListener {
            popupWindow.dismiss()
            clearDim(viewGroup)
        }

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun applyDim(parent: ViewGroup, dimAmount: Float) {
        val dim: Drawable = ColorDrawable(Color.BLACK)
        dim.setBounds(0, 0, parent.width, parent.height)
        dim.alpha = (255 * dimAmount).toInt()
        val overlay = parent.overlay
        overlay.add(dim)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun clearDim(parent: ViewGroup) {
        val overlay = parent.overlay
        overlay.clear()
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

