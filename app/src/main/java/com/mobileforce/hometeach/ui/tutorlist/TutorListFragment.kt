package com.mobileforce.hometeach.ui.tutorlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileforce.hometeach.databinding.FragmentAllTutorsBinding
import com.mobileforce.hometeach.models.TutorAllModel
import kotlinx.android.synthetic.main.fragment_ongoing.*
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 * Use the [TutorListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TutorListFragment : Fragment() {

    private lateinit var binding: FragmentAllTutorsBinding
    private val viewModel: TutorListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllTutorsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allTutorsList:MutableList<TutorAllModel> = mutableListOf()
        allTutorsList.add(TutorAllModel("1L","Alaye-Chris","profile_image","I teach with calmness and encouragement. My lessons are not boring and i can accomodate student’s with.....Read more","Chemistry Tutor",2000,"4.6/5"))
        allTutorsList.add(TutorAllModel("2L","Alaye-Chris","profile_image","I teach with calmness and encouragement. My lessons are not boring and i can accomodate student’s with.....Read more","Chemistry Tutor",2000,"4.6/5"))
        allTutorsList.add(TutorAllModel("3L","Alaye-Chris","profile_image","I teach with calmness and encouragement. My lessons are not boring and i can accomodate student’s with.....Read more","Chemistry Tutor",2000,"4.6/5"))
        allTutorsList.add(TutorAllModel("4L","Alaye-Chris","profile_image","I teach with calmness and encouragement. My lessons are not boring and i can accomodate student’s with.....Read more","Chemistry Tutor",2000,"4.6/5"))
        allTutorsList.add(TutorAllModel("5L","Alaye-Chris","profile_image","I teach with calmness and encouragement. My lessons are not boring and i can accomodate student’s with.....Read more","Chemistry Tutor",2000,"4.6/5"))

        val adapter = TutorListRecyclerAdapter()
        adapter.submitList(allTutorsList)
        binding.tutorAllList.adapter = adapter
        binding.tutorAllList.hasFixedSize()

    }
}