package com.example.hometeach.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hometeach.datamodel.OngoingClassesDataModel
import com.example.hometeach.R
import kotlinx.android.synthetic.main.fragment_parent_student_homepage.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ParentStudentHomepageFragment : Fragment() {

    private lateinit var ongoing_classes_recycler: RecyclerView
    private lateinit var upcoming_classes_recycler: RecyclerView
    private lateinit var top_tutors_recycler: RecyclerView
    private lateinit var ongoing_classes_list:MutableList<OngoingClassesDataModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent_student_homepage, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ongoing_classes_recycler = view.findViewById(R.id.ongoing_classes_recycler_view)
        upcoming_classes_recycler = view.findViewById(R.id.upcoming_classes_recycler_view)
        top_tutors_recycler = view.findViewById(R.id.top_tutors_recycler_view)
        val notification = notification
        val filter = filter
        val top_tutors = top_tutors_view_all
        val upcoming_classes = upcoming_view_all
        activity
        ongoing_classes_recycler.apply {
            layoutManager=LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        }
        ongoing_classes_recycler.setHasFixedSize(true)


    }
}