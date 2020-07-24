package com.mobileforce.hometeach.ui.home


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.RecyclerViewAdapter
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.data.sources.local.AppDataBase
import com.mobileforce.hometeach.databinding.FragmentHomePageParentBinding
import com.mobileforce.hometeach.databinding.FragmentHomePageTutorBinding
import com.mobileforce.hometeach.databinding.ListItemClassOngoingParentDashBoardBinding
import com.mobileforce.hometeach.databinding.ListItemClassUpcomingParentDashBoardBinding
import com.mobileforce.hometeach.models.*
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.TutorOngoingClassesAdapter
import com.mobileforce.hometeach.ui.home.student.OngoingClassViewHolderStudentDashBoard
import com.mobileforce.hometeach.ui.home.student.UpcomingClassViewHolderStudentDashBoard
import com.mobileforce.hometeach.ui.home.student.toptutors.TopTutorsAdapter
import com.mobileforce.hometeach.ui.home.student.toptutors.TopTutorsListItemListener
import com.mobileforce.hometeach.ui.signin.LoginActivity
import com.mobileforce.hometeach.utils.AppConstants.USER_STUDENT
import com.mobileforce.hometeach.utils.AppConstants.USER_TUTOR
import com.mobileforce.hometeach.utils.PreferenceHelper
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.toast
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
    private lateinit var topTutorsAdapter: TopTutorsAdapter

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
        //<---------------------------------TopTutorList Setup------------------------------------------>//
        viewModel.getTutorList()
        topTutorsAdapter = TopTutorsAdapter(TopTutorsListItemListener { tutor ->
            if (tutor != null) {
                findNavController().navigate(R.id.action_tutorHomePageFragment_to_tutorDetailsFragment)
            }
        })
        viewModel.tutorList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    bindingParent.topTutorsLoader.visibility = View.GONE
                    if (result.data != null) {
                        val list = result.data
                        topTutorsAdapter.submitList(list.take(3))
                        bindingParent.topTutorsRecyclerView.visibility = View.VISIBLE
                        bindingParent.topTutorsRecyclerView.adapter = topTutorsAdapter
                    } else {
                        Snackbar.make(
                            requireView(),
                            "No Tutors found!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                is Result.Loading -> {
                    bindingParent.topTutorsRecyclerView.visibility = View.GONE
                    bindingParent.topTutorsLoader.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    bindingParent.topTutorsLoader.visibility = View.GONE
                    bindingParent.topTutorsRecyclerView.visibility = View.GONE
                    Snackbar.make(
                        requireView(),
                        "Oops! An error occured, Swipe down to retry!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        bindingParent.viewAllTutorText.setOnClickListener {
            findNavController().navigate(R.id.action_tutorHomePageFragment_to_tutorsAllFragment)
        }

        //<--------------------------------- End - TopTutorList Setup------------------------------------------>//



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
            viewModel.logOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        val onGoingAdapter = object :
            RecyclerViewAdapter<OngoingClassModelTutor>(TutorOngoingClassesAdapter.OngoingClassesDiffCallBack()) {
            override fun getLayoutRes(model: OngoingClassModelTutor): Int =
                R.layout.list_item_class_ongoing_parent_dash_board

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
            override fun getLayoutRes(model: UpComingClassModel): Int =
                R.layout.list_item_class_upcoming_parent_dash_board

            override fun getViewHolder(
                view: View,
                recyclerViewAdapter: RecyclerViewAdapter<UpComingClassModel>
            ): ViewHolder<UpComingClassModel> {

                return UpcomingClassViewHolderStudentDashBoard(
                    ListItemClassUpcomingParentDashBoardBinding.bind(view)
                )
            }

        }

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
    }

    private fun setUpForTutor() {


        lifecycleScope.launch {
            val user = db.userDao().getUser()
            bindingTutor.username.text = " $user.full_name"
            bindingTutor.totalbalance.text = "BALANCE"
            bindingTutor.totalstudent.text = "TOTAL STUDENT"
            bindingTutor.totalreviews.text = "TOTAL REVIEWS"
            bindingTutor.totalprofilevisits.text = "PROFILE VISITS"
        }


//        bindingTutor.root.findViewById<LinearLayout>(R.id.mybanks).setOnClickListener {
//            //findNavController().navigate(R.id.myBanks)
//            toast(message = "Not yet Implemented: To be done soon", length = Toast.LENGTH_SHORT)
//        }
//        bindingTutor.root.findViewById<LinearLayout>(R.id.card_details).setOnClickListener {
//            //findNavController().navigate(R.id.tutorCardDetails)
//            toast(message = "Not yet Implemented: To be done soon", length = Toast.LENGTH_SHORT)
//        }
//        bindingTutor.root.findViewById<LinearLayout>(R.id.withdrawal).setOnClickListener {
//            //findNavController().navigate(R.id.makeWithdrawalFragment)
//            toast(message = "Not yet Implemented: To be done soon", length = Toast.LENGTH_SHORT)
//        }

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

            profile?.let {
                bindingTutor.reviewCount.text = (profile.rating_count ?: 0).toString()
            }


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
                    updateDateInView()
                }, year, month, day
            )
        }
        dpd?.show()
        if (dpd != null) {
            dpd.show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/DD/YYYY" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        textView_date?.text = sdf.format(c.time)

    }

    fun OnUserclicked(datamodel: TutorClassesDataModel, position: Int) {
        //OPEN INTENT  OR FRAGMENT TO EDIT THE TUTORS CLASSES
    }
}