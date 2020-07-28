package com.mobileforce.hometeach.ui.classes.parentstudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        viewModel.getStudentClasses()
        parentUpcomingClassesAdapter = ParentUpcomingClassesAdapter()

        viewModel.studentClasses.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                Result.Loading -> {
                }
                is Result.Success -> {
                    binding.recyclerView.apply {
                        adapter = parentUpcomingClassesAdapter
                    }
                    val classes = result.data!!.StudentClasses
                    if (classes.isNullOrEmpty()) {
                        binding.tvNoUpcoming.makeVisible()
                        binding.recyclerView.makeInvisible()
                    } else {
                        binding.tvNoUpcoming.makeInvisible()
                        binding.recyclerView.makeVisible()
                        parentUpcomingClassesAdapter.submitList(classes)
                    }
                }
                is Result.Error -> {
                    binding.root.snack(message = result.exception.localizedMessage)
                }
            }
        })
    }
}