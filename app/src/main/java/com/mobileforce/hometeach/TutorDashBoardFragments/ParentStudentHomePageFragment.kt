package com.mobileforce.hometeach.TutorDashBoardFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.models.OngoingClassesDataModel
import com.mobileforce.hometeach.models.ToptutorsDataModel
import com.mobileforce.hometeach.models.UpcomingClassesDataModel
import kotlinx.android.synthetic.main.fragment_parent_student_home_page.*

//import kotlinx.android.synthetic.main.fragment_parent_student_homepage.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ParentStudentHomePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class ParentStudentHomepageFragment : Fragment() {

    private lateinit var ongoing_classes_recycler: RecyclerView
    private lateinit var upcoming_classes_recycler: RecyclerView
    private lateinit var top_tutors_recycler: RecyclerView
    private lateinit var ongoing_classes_list:MutableList<OngoingClassesDataModel>
    private lateinit var upcoming_classes_list:MutableList<UpcomingClassesDataModel>
    private lateinit var top_tutors:MutableList<ToptutorsDataModel>
    lateinit var searchView: SearchView
    lateinit var username: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent_student_home_page, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = view.findViewById(R.id.username)
        username=view.findViewById(R.id.username)
        searchView = view.findViewById(R.id.search)
        ongoing_classes_recycler = view.findViewById(R.id.ongoing_classes_recycler_view)
        upcoming_classes_recycler = view.findViewById(R.id.upcoming_classes_recycler_view)
        top_tutors_recycler = view.findViewById(R.id.top_tutors_recycler_view)
        val notification =  notification
        val filter = filter
        val top_tutors = top_tutors_view_all
        val upcoming_classes = upcoming_view_all
        val ongoing_classes = ongoing_view_all
        activity
        ongoing_classes_recycler.apply {
            layoutManager=
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false) as RecyclerView.LayoutManager?
            //adapter = OngoingClassesAdapter(ongoing_classes_list)

        }
        ongoing_classes_recycler.setHasFixedSize(true)


        upcoming_classes_recycler.apply {
            layoutManager=
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false) as RecyclerView.LayoutManager?
        }
        upcoming_classes_recycler.setHasFixedSize(true)

        top_tutors_recycler.apply {
            layoutManager= LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        }
        top_tutors_recycler.setHasFixedSize(true)


    }
}