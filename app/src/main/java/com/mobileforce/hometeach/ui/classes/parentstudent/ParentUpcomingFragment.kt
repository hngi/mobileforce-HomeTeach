package com.mobileforce.hometeach.ui.classes.parentstudent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mobileforce.hometeach.databinding.FragmentParentUpcomingBinding
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.ParentUpcomingClassesAdapter
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.makeInvisible
import com.mobileforce.hometeach.utils.makeVisible
import com.mobileforce.hometeach.utils.snack
import org.koin.android.viewmodel.ext.android.viewModel


class ParentUpcomingFragment : Fragment() {

    private lateinit var binding: FragmentParentUpcomingBinding
    private lateinit var parentUpcomingClassesAdapter: ParentUpcomingClassesAdapter
    private val viewModel: ParentUpcomingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParentUpcomingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getStudentClassRequest()
        parentUpcomingClassesAdapter = ParentUpcomingClassesAdapter()

        viewModel.studentClassRequest.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                Result.Loading -> {}
                is Result.Success -> {
                    binding.recyclerView.apply {
                        adapter = parentUpcomingClassesAdapter
                    }
                    val requests = result.data!!.requests
                    if (requests.isEmpty()) {
                        binding.tvNoUpcoming.makeVisible()
                        binding.recyclerView.makeInvisible()
                    } else {
                        binding.tvNoUpcoming.makeInvisible()
                        binding.recyclerView.makeVisible()
                        parentUpcomingClassesAdapter.submitList(requests)
                    }
                }
                is Result.Error -> {
                    binding.root.snack(message = result.exception.localizedMessage)
                }
            }
        })
    }
}