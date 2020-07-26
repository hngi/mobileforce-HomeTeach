package com.mobileforce.hometeach.ui.classes.parentstudent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mobileforce.hometeach.databinding.FragmentParentOngoingBinding
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.ParentOngoingClassesAdapter
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.makeInvisible
import com.mobileforce.hometeach.utils.makeVisible
import com.mobileforce.hometeach.utils.snack
import org.koin.android.viewmodel.ext.android.viewModel


class ParentOngoingFragment : Fragment() {
    private lateinit var binding: FragmentParentOngoingBinding
    private lateinit var parentOngoingClassesAdapter: ParentOngoingClassesAdapter
    private val viewModel: ParentOngoingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParentOngoingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getStudentClassRequest()
        parentOngoingClassesAdapter = ParentOngoingClassesAdapter()

        viewModel.studentClassRequest.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                Result.Loading -> {}
                is Result.Success -> {
                    binding.recyclerview.apply {
                        adapter = parentOngoingClassesAdapter
                    }
                    val requests = result.data!!.requests
                    if (requests.isEmpty()) {
                        binding.tvNoOngoing.makeVisible()
                        binding.recyclerview.makeInvisible()
                    } else {
                        binding.tvNoOngoing.makeInvisible()
                        binding.recyclerview.makeVisible()
                        parentOngoingClassesAdapter.submitList(requests)
                    }
                }
                is Result.Error -> {
                    binding.root.snack(message = result.exception.localizedMessage)
                }
            }
        })
    }
}