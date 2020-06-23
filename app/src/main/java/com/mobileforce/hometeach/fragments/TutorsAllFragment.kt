package com.mobileforce.hometeach.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.AllTutorRecyclerAdapter
import com.mobileforce.hometeach.models.TutorAllModel
import kotlinx.android.synthetic.main.fragment_ongoing.*


/**
 * A simple [Fragment] subclass.
 * Use the [TutorsAllFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TutorsAllFragment : Fragment() {

    private lateinit var all_tutors_list:MutableList<TutorAllModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ongoing, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        // RecyclerView node initialized here
        all_tutors_list = mutableListOf()
        all_tutors_list.add(TutorAllModel("Alaye-Chris","profile_image","I teach with calmness and encouragement. My lessons are not boring and i can accomodate student’s with.....Read more","Chemistry Tutor",2000,"4.6/5"))
        all_tutors_list.add(TutorAllModel("Alaye-Chris","profile_image","I teach with calmness and encouragement. My lessons are not boring and i can accomodate student’s with.....Read more","Chemistry Tutor",2000,"4.6/5"))
        all_tutors_list.add(TutorAllModel("Alaye-Chris","profile_image","I teach with calmness and encouragement. My lessons are not boring and i can accomodate student’s with.....Read more","Chemistry Tutor",2000,"4.6/5"))
        all_tutors_list.add(TutorAllModel("Alaye-Chris","profile_image","I teach with calmness and encouragement. My lessons are not boring and i can accomodate student’s with.....Read more","Chemistry Tutor",2000,"4.6/5"))
        all_tutors_list.add(TutorAllModel("Alaye-Chris","profile_image","I teach with calmness and encouragement. My lessons are not boring and i can accomodate student’s with.....Read more","Chemistry Tutor",2000,"4.6/5"))

//        ongoing_classes_list = OngoingClassModel.getClassesFromFile("data.json", requireContext())
//        Log.d("count",ongoing_classes_list.size.toString())

        val adapter =  AllTutorRecyclerAdapter()
        adapter.submitList(all_tutors_list)
        classes_recycler_view.adapter = adapter
        classes_recycler_view.hasFixedSize()
//        classes_recycler_view.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
//        classes_recycler_view.setHasFixedSize(true)

    }
    companion object {
        fun newInstance(): TutorsAllFragment =
            TutorsAllFragment()
    }
}