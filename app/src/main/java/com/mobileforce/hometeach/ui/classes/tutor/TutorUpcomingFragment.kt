package com.mobileforce.hometeach.ui.classes.tutor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.mobileforce.hometeach.adapters.TutorUpcomingAdapter
import com.mobileforce.hometeach.databinding.FragmentTutorUpcomingBinding
import com.mobileforce.hometeach.models.Schedule
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.TutorOngoingClassesAdapter
import org.koin.android.ext.android.get


class TutorUpcomingFragment : Fragment() {
    private lateinit var binding: FragmentTutorUpcomingBinding
    private lateinit var tutorOngoingClassesAdapter: TutorOngoingClassesAdapter
    private val viewModel: TutorUpcomingViewModel = get<TutorUpcomingViewModel>()
    lateinit var scheduleList:List<Schedule>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorUpcomingBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getTutorSchedules()
        viewModel.tutorUpcoming.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.INVISIBLE
            if (it.schedules.isNullOrEmpty())
            {
                Toast.makeText(activity, "SORRY YOU HAVE NO CLASSES", Toast.LENGTH_SHORT).show()
            }
            scheduleList = it.schedules
//            binding.progressBar.visibility = View.INVISIBLE
            val adapter = TutorUpcomingAdapter(scheduleList)
            val recyclerView = binding.recyclerView
            recyclerView.adapter = adapter

        })

    }

}