package com.mobileforce.hometeach.ui.classes.parentstudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mobileforce.hometeach.databinding.FragmentParentRequestBinding
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.ParentRequestClassesAdapter
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.makeInvisible
import com.mobileforce.hometeach.utils.makeVisible
import com.mobileforce.hometeach.utils.snack
import org.koin.android.viewmodel.ext.android.viewModel


class ParentRequestFragment : Fragment() {
    private lateinit var binding: FragmentParentRequestBinding
    private lateinit var requestClassesAdapter: ParentRequestClassesAdapter
    private val viewModel: ParentRequestViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParentRequestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getStudentClassRequest()
        requestClassesAdapter = ParentRequestClassesAdapter()

        viewModel.studentClassRequest.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                Result.Loading -> {
                }
                is Result.Success -> {
                    binding.recyclerview.apply {
                        adapter = requestClassesAdapter
                    }
                    val requests = result.data!!.requests
                    if (requests.isEmpty()) {
                        binding.tvNoRequest.makeVisible()
                        binding.recyclerview.makeInvisible()
                    } else {
                        binding.tvNoRequest.makeInvisible()
                        binding.recyclerview.makeVisible()
                        requestClassesAdapter.submitList(requests)
                    }
                }
                is Result.Error -> {
                    binding.root.snack(message = result.exception.localizedMessage)
                }
            }
        })
    }

}