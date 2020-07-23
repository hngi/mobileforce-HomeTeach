package com.mobileforce.hometeach.ui.booktutor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.BookTutorFormBinding
import com.mobileforce.hometeach.ui.tutorlist.DialogData
import com.mobileforce.hometeach.ui.tutorlist.SelectDateDialog
import com.mobileforce.hometeach.utils.Result
import com.tiper.MaterialSpinner
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Mayokun Adeniyi on 23/07/2020.
 */

class BookTutorFragment : Fragment(), SelectDateDialog.SelectDateListener {
    private lateinit var binding: BookTutorFormBinding
    private lateinit var popupDialog: SelectDateDialog
    private val viewModel: BookTutorViewModel by viewModel()
    private var courseSet = false
    private var gradeSet = false
    private var calendarDialogData: DialogData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BookTutorFormBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookTutorFragmentArgs by navArgs<BookTutorFragmentArgs>()
        val tutor = bookTutorFragmentArgs.tutor
        binding.tutor = tutor
        viewModel.setTutor(tutor)
        observeViewModels()
        setupSpinners()
        popupDialog = SelectDateDialog()
        binding.selectTimeDateCard.setOnClickListener {
            popupDialog.showPopupWindow(requireView(), this)
        }
        binding.bookTutorButton.setOnClickListener {
           if (courseSet && gradeSet && calendarDialogData != null){
               val subject = binding.courseSpinner.selectedItem as String
               val grade = binding.gradeSpinner.selectedItem as String
               viewModel.getTutorService(calendarDialogData!!,subject,grade)
           }else if (!courseSet){
               Toast.makeText(requireContext(),"Please select a course!",Toast.LENGTH_SHORT).show()
           }else if (!gradeSet){
               Toast.makeText(requireContext(),"Please select a grade!",Toast.LENGTH_SHORT).show()
           }else if (calendarDialogData == null){
               Toast.makeText(requireContext(),"Please select the time and date!",Toast.LENGTH_SHORT).show()
           }
        }
    }

    private fun setupSpinners() {
        val courseListener by lazy {
            object : MaterialSpinner.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: MaterialSpinner,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    courseSet = true
                    parent.focusSearch(View.FOCUS_UP)?.requestFocus()
                }

                override fun onNothingSelected(parent: MaterialSpinner) {
                    courseSet = false
                }
            }
        }

        val gradeListener by lazy {
            object : MaterialSpinner.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: MaterialSpinner,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    gradeSet = true
                    parent.focusSearch(View.FOCUS_UP)?.requestFocus()
                }

                override fun onNothingSelected(parent: MaterialSpinner) {
                    gradeSet = false
                }
            }
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.tutor_subjects,
            android.R.layout.simple_spinner_item
        ).let {
            it.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
            binding.courseSpinner.apply {
                adapter = it
                onItemSelectedListener = courseListener
            }
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.course_grade,
            android.R.layout.simple_spinner_item
        ).let {
            it.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
            binding.gradeSpinner.apply {
                adapter = it
                onItemSelectedListener = gradeListener
            }
        }
    }

    @SuppressLint("NewApi")
    private fun observeViewModels() {

        viewModel.getUser().observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                viewModel.setUser(result)
            }
        })

        viewModel.serviceApproved.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    popupDialog.applyDim(requireView().parent as ViewGroup, 0.5f)
                    popupDialog.showSuccessDialog(requireView(), result.data)
                }

                is Result.Loading -> {
                    popupDialog.clearDim(requireView().parent as ViewGroup)
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    Snackbar.make(
                        requireView(),
                        "Oops! An error occurred! Check your connection and try again ".plus(result.exception),
                        Snackbar.LENGTH_LONG
                    ).show()
                    popupDialog.clearDim(requireView().parent as ViewGroup)
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onApproveClicked(dialogData: DialogData) {
        calendarDialogData = dialogData
        val listOfDates = dialogData.dates
        binding.textView6.text =
            "${listOfDates.first()} to ${listOfDates.last()} - ${dialogData.fromHour}:${dialogData.fromMinute} to ${dialogData.toHour}:${dialogData.toMinute}"
    }
}