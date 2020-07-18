package com.mobileforce.hometeach.ui.tutorlist

import android.annotation.SuppressLint
import android.app.SearchManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.databinding.FragmentAllTutorsBinding
import com.mobileforce.hometeach.models.TutorModel
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.toDomainModel
import org.koin.android.viewmodel.ext.android.viewModel


class TutorListFragment : Fragment(), SelectDateDialog.SelectDateListener {

    private lateinit var binding: FragmentAllTutorsBinding
    private val viewModel: TutorListViewModel by viewModel()
    private lateinit var adapter: TutorListRecyclerAdapter
    private lateinit var popupDialog: SelectDateDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllTutorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.swipeLayout.setOnRefreshListener {
            viewModel.refreshTutorList()
            binding.swipeLayout.isRefreshing = false
        }

        val allTutorsList: MutableList<TutorModel> = mutableListOf()
        allTutorsList.add(
            TutorModel(
                "3e083af1-2b36-442a-9d33-75a317bc95d1",
                "James Harden",
                "profile_image",
                "I teach with calmness and encouragement. My lessons are not boring and i can accommodate student’s with low affinity to studying. I employ modern schema for tutoring with interactive guides and learning systems. Having schooled at different educational organizations coupled with my NCE certificate, I can assure you premium success with me as your Home-Teacher.",
                "Chemistry Tutor",
                "2000",
                2.3
            )
        )
        allTutorsList.add(
            TutorModel(
                "3e083af1-2b36-442a-9d33-75a317bc95d1",
                "John Wick",
                "profile_image",
                "I teach with calmness and encouragement. My lessons are not boring and i can accommodate student’s with low affinity to studying. I employ modern schema for tutoring with interactive guides and learning systems. Having schooled at different educational organizations coupled with my NCE certificate, I can assure you premium success with me as your Home-Teacher.",
                "English Tutor",
                "2000",
                2.3
            )
        )
        allTutorsList.add(
            TutorModel(
                "3e083af1-2b36-442a-9d33-75a317bc95d1",
                "Micheal Kean",
                "profile_image",
                "I teach with calmness and encouragement. My lessons are not boring and i can accommodate student’s with low affinity to studying. I employ modern schema for tutoring with interactive guides and learning systems. Having schooled at different educational organizations coupled with my NCE certificate, I can assure you premium success with me as your Home-Teacher.",
                "Physics Tutor",
                "2000",
                2.3
            )
        )
        allTutorsList.add(
            TutorModel(
                "3e083af1-2b36-442a-9d33-75a317bc95d1",
                "Tim Johnson",
                "profile_image",
                "I teach with calmness and encouragement. My lessons are not boring and i can accommodate student’s with low affinity to studying. I employ modern schema for tutoring with interactive guides and learning systems. Having schooled at different educational organizations coupled with my NCE certificate, I can assure you premium success with me as your Home-Teacher.",
                "Chemistry Tutor",
                "2000",
                2.3
            )
        )
        allTutorsList.add(
            TutorModel(
                "3e083af1-2b36-442a-9d33-75a317bc95d1",
                "Daniel Kim",
                "profile_image",
                "I teach with calmness and encouragement. My lessons are not boring and i can accommodate student’s with low affinity to studying. I employ modern schema for tutoring with interactive guides and learning systems. Having schooled at different educational organizations coupled with my NCE certificate, I can assure you premium success with me as your Home-Teacher.",
                "Mathematics Tutor",
                "2000",
                2.3
            )
        )

        //Get the list of tutors
        viewModel.getTutorList()


        popupDialog = SelectDateDialog()
        observeViewModels()
        adapter = TutorListRecyclerAdapter(TutorListItemListener { tutorId ->
            if (tutorId != null) {
                val dialog = SelectDateDialog()
                dialog.showPopupWindow(requireView(), this)
                viewModel.setTutorId(tutorId)
            }
        })
        adapter.submitList(allTutorsList)

        binding.tutorAllList.adapter = adapter

        binding.search.isSubmitButtonEnabled = true
        binding.search.setIconifiedByDefault(false)

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getItemsFromDb(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    getItemsFromDb(newText)
                }
                return true
            }
        })


        val closeButton: View? =
            binding.search.findViewById(androidx.appcompat.R.id.search_close_btn)
        closeButton?.setOnClickListener {
            binding.search.setQuery("", false)
            binding.tutorAllList.adapter = adapter
        }

    }

    private fun getItemsFromDb(query: String) {
        viewModel.searchForTutor(query).observe(viewLifecycleOwner, Observer { result ->
            if (result == null || result.isEmpty()) {
                return@Observer
            }
            val newAdapter = TutorListRecyclerAdapter(TutorListItemListener { tutorId ->
                if (tutorId != null) {
                    val dialog = SelectDateDialog()
                    dialog.showPopupWindow(requireView(), this)
                    viewModel.setTutorId(tutorId)
                }
            })
            newAdapter.submitList(result.map { it.toDomainModel() })
            binding.tutorAllList.adapter = newAdapter
        })
    }


    @SuppressLint("NewApi")
    private fun observeViewModels() {
        viewModel.tutorList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    binding.progressBar2.visibility = View.GONE
                    if (result.data != null) {
                        adapter.submitList(result.data)
                    } else {
                        Snackbar.make(
                            requireView(),
                            "No Tutors found! Swipe down to retry!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                is Result.Loading -> {
                    binding.progressBar2.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.progressBar2.visibility = View.GONE
                    Snackbar.make(
                        requireView(),
                        "Oops! An error occured, Swipe down to retry!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        viewModel.getUser().observe(viewLifecycleOwner, Observer { result ->
            if (result != null){
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
        viewModel.getTutorService(dialogData)
    }
}