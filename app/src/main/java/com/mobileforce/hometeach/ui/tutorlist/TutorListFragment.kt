package com.mobileforce.hometeach.ui.tutorlist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.databinding.FragmentAllTutorsBinding
import com.mobileforce.hometeach.models.TutorAllModel
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

        //Get the list of tutors
        //viewModel.getTutorList()

        popupDialog = SelectDateDialog()
        observeViewModels()
        val allTutorsList: MutableList<TutorAllModel> = mutableListOf()
        allTutorsList.add(
            TutorAllModel(
                "1L",
                "Alaye-Chris",
                "profile_image",
                "I teach with calmness and encouragement. My lessons are not boring and i can accommodate student’s with low affinity to studying. I employ modern schema for tutoring with interactive guides and learning systems. Having schooled at different educational organizations coupled with my NCE certificate, I can assure you premium success with me as your Home-Teacher.",
                "Chemistry Tutor",
                2000,
                "4.6/5"
            )
        )
        allTutorsList.add(
            TutorAllModel(
                "2L",
                "Alaye-Chris",
                "profile_image",
                "I teach with calmness and encouragement. My lessons are not boring and i can accommodate student’s with low affinity to studying. I employ modern schema for tutoring with interactive guides and learning systems. Having schooled at different educational organizations coupled with my NCE certificate, I can assure you premium success with me as your Home-Teacher.",
                "Chemistry Tutor",
                2000,
                "4.6/5"
            )
        )
        allTutorsList.add(
            TutorAllModel(
                "3L",
                "Alaye-Chris",
                "profile_image",
                "I teach with calmness and encouragement. My lessons are not boring and i can accommodate student’s with low affinity to studying. I employ modern schema for tutoring with interactive guides and learning systems. Having schooled at different educational organizations coupled with my NCE certificate, I can assure you premium success with me as your Home-Teacher.",
                "Chemistry Tutor",
                2000,
                "4.6/5"
            )
        )
        allTutorsList.add(
            TutorAllModel(
                "4L",
                "Alaye-Chris",
                "profile_image",
                "I teach with calmness and encouragement. My lessons are not boring and i can accommodate student’s with low affinity to studying. I employ modern schema for tutoring with interactive guides and learning systems. Having schooled at different educational organizations coupled with my NCE certificate, I can assure you premium success with me as your Home-Teacher.",
                "Chemistry Tutor",
                2000,
                "4.6/5"
            )
        )
        allTutorsList.add(
            TutorAllModel(
                "5L",
                "Alaye-Chris",
                "profile_image",
                "I teach with calmness and encouragement. My lessons are not boring and i can accommodate student’s with low affinity to studying. I employ modern schema for tutoring with interactive guides and learning systems. Having schooled at different educational organizations coupled with my NCE certificate, I can assure you premium success with me as your Home-Teacher.",
                "Chemistry Tutor",
                2000,
                "4.6/5"
            )
        )

        adapter = TutorListRecyclerAdapter(TutorListItemListener { tutorId ->
            if (tutorId != null) {
                val dialog = SelectDateDialog()
                dialog.showPopupWindow(requireView(), this)
                viewModel.setTutorId(tutorId)
            }
        })


        //TODO Remove this dummy data
        adapter.submitList(allTutorsList)
        binding.tutorAllList.adapter = adapter
        binding.tutorAllList.hasFixedSize()

        binding.search.isSubmitButtonEnabled = true
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    //getItemsFromDb(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    //getItemsFromDb(newText)
                }
                return true
            }
        })

    }

    private fun getItemsFromDb(query: String) {
        viewModel.searchForTutor(query).observe(viewLifecycleOwner, Observer { result ->
            result?.let {
                adapter.submitList(result.map { it.toDomainModel() })
            }
        })
    }


    private fun observeViewModels() {
        viewModel.tutorList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    binding.progressBar2.visibility = View.GONE
                    if (result.data != null) {
                        //adapter.submitList(result.data)
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

        viewModel.serviceApproved.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    popupDialog.showSuccessDialog(requireView())
                }

                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    Snackbar.make(
                        requireView(),
                        "Oops! An error occurred, try again.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onApproveClicked(dialogData: DialogData) {
        viewModel.getTutorService(dialogData)
    }
}