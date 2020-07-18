package com.mobileforce.hometeach.ui.home


import android.app.DatePickerDialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.*

import android.widget.LinearLayout
import android.widget.RelativeLayout

import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.google.android.material.button.MaterialButton
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.RecyclerViewAdapter
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.data.sources.local.AppDataBase
import com.mobileforce.hometeach.databinding.*
import com.mobileforce.hometeach.models.*
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.TutorOngoingClassesAdapter
import com.mobileforce.hometeach.ui.home.student.OngoingClassViewHolderStudentDashBoard
import com.mobileforce.hometeach.ui.home.student.TopTutorsViewHolderStudentDashBoard
import com.mobileforce.hometeach.ui.home.student.UpcomingClassViewHolderStudentDashBoard
import com.mobileforce.hometeach.ui.signin.LoginActivity
import com.mobileforce.hometeach.utils.AppConstants.USER_STUDENT
import com.mobileforce.hometeach.utils.AppConstants.USER_TUTOR

import com.mobileforce.hometeach.utils.PreferenceHelper
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Authored by enyason
 */
class HomePageFragment : Fragment() {

    private val pref: PreferenceHelper by inject()

    private val db: AppDataBase by inject()

    private lateinit var bindingParent: FragmentHomePageParentBinding
    private lateinit var bindingTutor: FragmentHomePageTutorBinding
    private val viewModel: HomePageViewModel by viewModel()

    var button_modify: Button? = null
    var textView_date: TextView? = null
    var c = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)

        return if (pref.userType == USER_STUDENT) {
            bindingParent = FragmentHomePageParentBinding.inflate(layoutInflater)
            bindingParent.root
        } else {
            bindingTutor = FragmentHomePageTutorBinding.inflate(layoutInflater)
            bindingTutor.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (pref.userType == USER_STUDENT) {
            setUpForStudent()
        } else if (pref.userType == USER_TUTOR) {
            setUpForTutor()
        }
    }

//    private fun DatePickerDialog(
//        homePageFragment: HomePageFragment,
//        dateSetListener: DatePickerDialog.OnDateSetListener,
//        get: Int,
//        get1: Int,
//        get2: Int
//    ): DatePickerDialog {
//        Calendar.getInstance().get(Calendar.YEAR)
//        Calendar.getInstance().get(Calendar.MONTH)
//        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
//
//    }

    private fun setUpForStudent() {

        viewModel.user.observe(viewLifecycleOwner, androidx.lifecycle.Observer { user ->

            user?.let {
                bindingParent.studentToolbar.title = "Welcome ${user.full_name}"
            }
        })

        bindingParent.root.findViewById<RelativeLayout>(R.id.actionMakepayment).setOnClickListener {
            findNavController().navigate(R.id.studentMakePaymentFragment)
        }

        bindingParent.root.findViewById<RelativeLayout>(R.id.actionCardDetails).setOnClickListener {
            findNavController().navigate(R.id.studentCardDetails)
        }

        bindingParent.root.findViewById<MaterialButton>(R.id.signOut).setOnClickListener {
            //viewModel.logOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
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

        bindingParent.viewAllTutorText.setOnClickListener {
            findNavController().navigate(R.id.action_tutorHomePageFragment_to_tutorsAllFragment)
        }
    }

    private fun setUpForTutor() {


        lifecycleScope.launch {
            val user = db.userDao().getUser()
            bindingTutor.username.text = " $user.full_name"
            bindingTutor.totalbalance.text = "BALANCE"
            bindingTutor.totalstudent.text = "TOTAL STUDENT"
            bindingTutor.totalreviews.text = "TOTAL REVIWS"
            bindingTutor.totalprofilevisits.text = "TOTAL PROFILE VISITS"
        }


        bindingTutor.root.findViewById<LinearLayout>(R.id.mybanks).setOnClickListener {
            findNavController().navigate(R.id.myBanks)
        }
        bindingTutor.root.findViewById<LinearLayout>(R.id.card_details).setOnClickListener {
            findNavController().navigate(R.id.tutorCardDetails)
        }
        bindingTutor.root.findViewById<LinearLayout>(R.id.withdrawal).setOnClickListener {
            findNavController().navigate(R.id.makeWithdrawalFragment)
        }

        val TutorDashboardModel = mutableListOf<TutorDashboardModel>(
            TutorDashboardModel(
                UUID.randomUUID().toString(),
                "TOATL STUDENT",
                "BALANCE",
                "PROFILE VISITS",
                "TOTAL REVIEWS"
            )
        )
        bindingTutor.modifyBtn.setOnClickListener {
            data_picker()
        }

        bindingTutor.signout.setOnClickListener {
            viewModel.logOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()

        }

        viewModel.user.observe(viewLifecycleOwner, androidx.lifecycle.Observer { user ->


        user?.let {
                bindingTutor.username.text = "Welcome ${user.full_name}"
            }
        })

        viewModel.profile.observe(viewLifecycleOwner, Observer { profile ->

            bindingTutor.reviewCount.text = (profile.rating_count ?: 0).toString()


        })
    }

    fun data_picker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = activity?.let {
            DatePickerDialog(
                it,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

//                    MONTHS[monthOfYear]
//                   dayOfMonth
                    //MONTHS[monthOfYear]

                    updateDateInView()
                },
                year,
                month,
                day
            )
        }

        if (dpd != null) {
            dpd.show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textView_date!!.text = sdf.format(c.getTime())

    }

    fun OnUserclicked(datamodel: TutorClassesDataModel, position: Int) {
        //OPEN INTENT  OR FRAGMENT TO EDIT THE TUTORS CLASSES
    }
}