package com.mobileforce.hometeach.ui.booktutor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.databinding.BookTutorFormBinding
import com.mobileforce.hometeach.ui.tutorlist.DialogData
import com.mobileforce.hometeach.ui.tutorlist.SelectDateDialog
import com.mobileforce.hometeach.utils.Result
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
        setupSpinners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val bookTutorFragmentArgs by navArgs<BookTutorFragmentArgs>()
        val tutor = bookTutorFragmentArgs.tutor
        binding.tutor = tutor
        viewModel.setTutor(tutor)
        observeViewModels()
        popupDialog = SelectDateDialog()
        binding.selectTimeDateCard.setOnClickListener {
            popupDialog.showPopupWindow(requireView(), this)
        }
        binding.bookTutorButton.setOnClickListener {
            if (courseSet && gradeSet && calendarDialogData != null) {
                val subject = binding.courseSpinner.text.toString().trim()
                val grade = binding.gradeSpinner.text.toString().trim()
                viewModel.getTutorService(calendarDialogData!!, subject, grade)
            } else if (!courseSet) {
                Toast.makeText(requireContext(), "Please select a course!", Toast.LENGTH_SHORT)
                    .show()
            } else if (!gradeSet) {
                Toast.makeText(requireContext(), "Please select a grade!", Toast.LENGTH_SHORT)
                    .show()
            } else if (calendarDialogData == null) {
                Toast.makeText(
                    requireContext(),
                    "Please select the time and date!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupSpinners() {

        binding.gradeSpinner.lifecycleOwner = viewLifecycleOwner
        binding.courseSpinner.lifecycleOwner = viewLifecycleOwner

        binding.courseSpinner.setOnSpinnerOutsideTouchListener { view: View, event: MotionEvent ->
            binding.courseSpinner.dismiss()
        }

        binding.gradeSpinner.setOnSpinnerOutsideTouchListener { view: View, motionEvent: MotionEvent ->
            binding.gradeSpinner.dismiss()
        }

        binding.courseSpinner.setOnSpinnerItemSelectedListener<String> { position: Int, item: String ->
            courseSet = true
        }
        binding.gradeSpinner.setOnSpinnerItemSelectedListener<String> { position: Int, item: String ->
            gradeSet = true
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