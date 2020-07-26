package com.mobileforce.hometeach.ui.tutorlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.databinding.FragmentAllTutorsBinding
import com.mobileforce.hometeach.models.TutorModel
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.toDomainModel
import org.koin.android.viewmodel.ext.android.viewModel


class TutorListFragment : Fragment(){

    private lateinit var binding: FragmentAllTutorsBinding
    private val viewModel: TutorListViewModel by viewModel()
    private lateinit var adapter: TutorListRecyclerAdapter

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

        viewModel.getTutorList()

        observeViewModels()
        adapter = TutorListRecyclerAdapter(TutorListItemListener { tutor ->
            if (tutor != null) {
                val action = TutorListFragmentDirections.actionTutorsAllFragmentToBookTutorFragment(tutor)
                findNavController().navigate(action)
            }
        }, TutorBodyClickListener { tutorModel ->
            if (tutorModel != null){
                val action = TutorListFragmentDirections.actionTutorsAllFragmentToTutorDetailsFragment(tutorModel)
                findNavController().navigate(action)
            }
        })

        binding.tutorAllList.adapter = adapter

        binding.search.isSubmitButtonEnabled = true
        binding.search.setIconifiedByDefault(false)

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getItemsFromDb(query.trim())
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    getItemsFromDb(newText.trim())
                    if (newText.isEmpty()){
                        binding.tutorAllList.adapter = adapter
                    }
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
            val newAdapter = TutorListRecyclerAdapter(TutorListItemListener { tutor ->
                if (tutor != null) {
                    val action = TutorListFragmentDirections.actionTutorsAllFragmentToBookTutorFragment(tutor)
                    findNavController().navigate(action)
                }
            }, TutorBodyClickListener { tutorModel ->
                if (tutorModel != null){
                    val action = TutorListFragmentDirections.actionTutorsAllFragmentToTutorDetailsFragment(tutorModel)
                    findNavController().navigate(action)
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
    }
}