package com.mobileforce.hometeach.ui.classes.tutor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mobileforce.hometeach.databinding.FragmentTutorUpcomingBinding
import com.mobileforce.hometeach.models.Schedule
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.TutorOngoingClassesAdapter
import org.koin.android.ext.android.get


class TutorUpcomingFragment : Fragment() {
    private lateinit var binding: FragmentTutorUpcomingBinding
    private lateinit var tutorOngoingClassesAdapter: TutorOngoingClassesAdapter
    private val viewModel: TutorUpcomingViewModel = get<TutorUpcomingViewModel>()
    lateinit var scheduleList:Schedule

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorUpcomingBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("coffee","INSIDE TUTOR REQUEST")
        viewModel.getTutorSchedules()
        viewModel.tutorUpcoming.observe(viewLifecycleOwner, Observer {

            Log.d("coffee",it.schedules.toString())
        })


        val recyclerView = binding.recyclerView

        recyclerView.adapter = tutorOngoingClassesAdapter


//        viewModel.getTutorRequest()
        viewModel.tutorUpcoming.observe(viewLifecycleOwner, Observer {

            Log.d("dev",it.toString())
        })

    }

}