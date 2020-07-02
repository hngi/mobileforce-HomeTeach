package com.mobileforce.hometeach.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.google.android.material.button.MaterialButton
import com.mobileforce.hometeach.AppConstants.USER_STUDENT
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.RecyclerViewAdapter
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.databinding.FragmentHomePageParentBinding
import com.mobileforce.hometeach.databinding.ListItemClassOngoingParentDashBoardBinding
import com.mobileforce.hometeach.databinding.ListItemClassUpcomingParentDashBoardBinding
import com.mobileforce.hometeach.databinding.ListItemTutorParentDashBoardBinding
import com.mobileforce.hometeach.local.PreferenceHelper
import com.mobileforce.hometeach.models.*
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.TutorOngoingClassesAdapter
import org.koin.android.ext.android.inject
import java.util.*

/**
 * Authored by enyason
 */
class HomePageFragment : Fragment() {

    private val pref: PreferenceHelper by inject()

    lateinit var bindingParent: FragmentHomePageParentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        println("user preference ${pref.userType}")

        return if (pref.userType == USER_STUDENT) {

            bindingParent = FragmentHomePageParentBinding.inflate(layoutInflater)
            bindingParent.root
        } else {
            /**
             * TODO this is just for testing purpose, change to appropriate binding
             */
            bindingParent = FragmentHomePageParentBinding.inflate(layoutInflater)
            bindingParent.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (pref.userType == USER_STUDENT) {
            setUpForStudent()
        }

    }

    private fun setUpForStudent() {

        bindingParent.root.findViewById<RelativeLayout>(R.id.actionMakepayment).setOnClickListener {
            // go to make payment
        }

        bindingParent.root.findViewById<RelativeLayout>(R.id.actionCardDetails).setOnClickListener {

            // go to card details
        }

        bindingParent.root.findViewById<MaterialButton>(R.id.signOut).setOnClickListener {

            //sign out
        }

        val onGoingAdapter = object :
            RecyclerViewAdapter<OngoingClassModelTutor>(TutorOngoingClassesAdapter.OngoingClassesDiffCallBack()) {
            override fun getLayoutRes() = R.layout.list_item_class_ongoing_parent_dash_board

            override fun getViewHolder(
                view: View,
                recyclerViewAdapter: RecyclerViewAdapter<OngoingClassModelTutor>
            ): ViewHolder<OngoingClassModelTutor> {

                return OngoingClassViewHolderStudentDashBoard(
                    ListItemClassOngoingParentDashBoardBinding.bind(view)
                )
            }

        }

        val upComingAdapter = object : RecyclerViewAdapter<UpComingClassModel>(upComingDiffUtil) {
            override fun getLayoutRes() = R.layout.list_item_class_upcoming_parent_dash_board

            override fun getViewHolder(
                view: View,
                recyclerViewAdapter: RecyclerViewAdapter<UpComingClassModel>
            ): ViewHolder<UpComingClassModel> {

                return UpcomingClassViewHolderStudentDashBoard(
                    ListItemClassUpcomingParentDashBoardBinding.bind(view)
                )
            }

        }

        val topTutorsAdapter = object : RecyclerViewAdapter<TopTutorModel>(topTutorDiffUtil) {
            override fun getLayoutRes() = R.layout.list_item_tutor_parent_dash_board

            override fun getViewHolder(
                view: View,
                recyclerViewAdapter: RecyclerViewAdapter<TopTutorModel>
            ): ViewHolder<TopTutorModel> {

                return TopTutorsViewHolderStudentDashBoard(
                    ListItemTutorParentDashBoardBinding.bind(view)
                )
            }

        }
        val topTutors = mutableListOf(
            TopTutorModel(
                UUID.randomUUID().toString(),
                "Rahman Django",
                4.6,
                "https://via.placeholder.com/300/09f/fff.png",
                "Physics",
                "I teach with calmness and encouragement. My lessons are not boring and i can accomodate student’s with.....Read more"
            ),
            TopTutorModel(
                UUID.randomUUID().toString(),
                "Enya Emmanuel",
                5.0,
                "https://via.placeholder.com/300/09f/fff.png",
                "Mathematics",
                "I use modern teaching aid to facilitate my lessons"
            ),
            TopTutorModel(
                UUID.randomUUID().toString(),
                "Enya Emmanuel",
                5.0,
                "https://via.placeholder.com/300/09f/fff.png",
                "Mathematics",
                "I use modern teaching aid to facilitate my lessons"
            )
        )

        val upComingClasses = mutableListOf(
            UpComingClassModel(
                UUID.randomUUID().toString(),
                "Physics",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Rahman Django",
                "https://via.placeholder.com/300/09f/fff.png",
                "Physics Tutor"
            ),
            UpComingClassModel(
                UUID.randomUUID().toString(),
                "Mathematics",
                "Tue, 13 July",
                "16:00 - 18:00",
                "John Doe",
                "https://via.placeholder.com/300/09f/fff.png",
                "MAth Tutor"
            ),
            UpComingClassModel(
                UUID.randomUUID().toString(),
                "Biology",
                "Tue, 9 July",
                "16:00 - 18:00",
                "Sophia Lee",
                "https://via.placeholder.com/300/09f/fff.png",
                "Biology Tutor"
            )
        )

        val ongoingClassesList: MutableList<OngoingClassModelTutor> = mutableListOf()
        ongoingClassesList.add(
            OngoingClassModelTutor(
                1,
                "Physics",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Rahman Django",
                "https://via.placeholder.com/300/09f/fff.png",
                40,
                1
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                2,
                "Biology",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "profile_image",
                100,
                4
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                3,
                "Mathematics",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "https://via.placeholder.com/300/09f/fff.png",
                100,
                3
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                4,
                "Chemistry",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Rahman Django",
                "profile_image",
                40,
                5
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                5,
                "Geography",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "profile_image",
                100,
                1
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                6,
                "Computer Science",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "https://via.placeholder.com/300/09f/fff.png",
                100,
                6
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                7,
                "Fishery",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Rahman Django",
                "profile_image",
                40,
                6
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                8,
                "Fine Arts",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "profile_image",
                100,
                2
            )
        )
        ongoingClassesList.add(
            OngoingClassModelTutor(
                9,
                "Drama",
                "Tue, 10 July",
                "16:00 - 18:00",
                "Alice Snow",
                "https://via.placeholder.com/300/09f/fff.png",
                100,
                6
            )
        )

        bindingParent.ongoingClassesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            adapter = onGoingAdapter
        }

        onGoingAdapter.submitList(ongoingClassesList)

        bindingParent.upcomingClassesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            adapter = upComingAdapter
        }

        upComingAdapter.submitList(upComingClasses)

        bindingParent.topTutorsRecyclerView.apply {

            layoutManager = LinearLayoutManager(requireContext())
            adapter = topTutorsAdapter
            ViewCompat.setNestedScrollingEnabled(this, false)
        }

        topTutorsAdapter.submitList(topTutors)


    }

}