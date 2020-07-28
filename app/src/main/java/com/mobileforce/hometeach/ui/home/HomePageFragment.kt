package com.mobileforce.hometeach.ui.home


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.RecyclerViewAdapter
import com.mobileforce.hometeach.adapters.ViewHolder
import com.mobileforce.hometeach.data.sources.local.AppDataBase
import com.mobileforce.hometeach.data.sources.remote.wrappers.StudentClass
import com.mobileforce.hometeach.data.sources.remote.wrappers.studentClassDiffUtil
import com.mobileforce.hometeach.databinding.FragmentHomePageParentBinding
import com.mobileforce.hometeach.databinding.FragmentHomePageTutorBinding
import com.mobileforce.hometeach.databinding.ListItemClassOngoingParentDashBoardBinding
import com.mobileforce.hometeach.databinding.ListItemClassUpcomingParentDashBoardBinding
import com.mobileforce.hometeach.models.TutorClassesDataModel
import com.mobileforce.hometeach.ui.home.student.OngoingClassViewHolderStudentDashBoard
import com.mobileforce.hometeach.ui.home.student.UpcomingClassViewHolderStudentDashBoard
import com.mobileforce.hometeach.ui.home.student.toptutors.TopTutorsAdapter
import com.mobileforce.hometeach.ui.home.student.toptutors.TopTutorsListItemListener
import com.mobileforce.hometeach.ui.signin.LoginActivity
import com.mobileforce.hometeach.utils.*
import com.mobileforce.hometeach.utils.AppConstants.USER_STUDENT
import com.mobileforce.hometeach.utils.AppConstants.USER_TUTOR
import com.mobileforce.hometeach.utils.Result
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
    private var textViewDate: TextView? = null
    private var c = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        //<--------------------------------- Start - TopTutorList Setup ------------------------------------------>//
        viewModel.getTutorList()
        topTutorsAdapter = TopTutorsAdapter(TopTutorsListItemListener { tutor ->
            if (tutor != null) {
                val action =
                    HomePageFragmentDirections.actionTutorHomePageFragmentToTutorDetailsFragment(
                        tutor
                    )
                findNavController().navigate(action)
            }
        })
        bindingParent.topTutorsRecyclerView.adapter = topTutorsAdapter

        viewModel.tutorList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    bindingParent.topTutorsLoader.visibility = View.GONE
                    if (result.data != null) {
                        val list = result.data
                        topTutorsAdapter.submitList(list.take(3))
                    } else {
                        Snackbar.make(
                            requireView(),
                            "No Tutors found!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                is Result.Loading -> {
                    bindingParent.topTutorsLoader.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    bindingParent.topTutorsLoader.visibility = View.GONE
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

        //<--------------------------------- End - TopTutorList Setup ------------------------------------------>//

        viewModel.user.observe(viewLifecycleOwner, androidx.lifecycle.Observer { user ->

            user?.let {
                bindingParent.studentToolbar.title = "Welcome ${user.full_name}"
            }
        })

        //<--------------------------------- Start - Upcoming and Ongoing class Setup ------------------------------------------>//
        viewModel.getStudentClasses()
        // Adapter for Upcoming classes
        val upComingAdapter = object : RecyclerViewAdapter<StudentClass>(studentClassDiffUtil) {
            override fun getLayoutRes(model: StudentClass): Int =
                R.layout.list_item_class_upcoming_parent_dash_board

            override fun getViewHolder(
                view: View,
                recyclerViewAdapter: RecyclerViewAdapter<StudentClass>
            ): ViewHolder<StudentClass> {

                return UpcomingClassViewHolderStudentDashBoard(
                    ListItemClassUpcomingParentDashBoardBinding.bind(view)
                )
            }
        }

        // Adapter for ongoing classes
        val onGoingAdapter = object :
            RecyclerViewAdapter<StudentClass>(studentClassDiffUtil) {
            override fun getLayoutRes(model: StudentClass): Int =
                R.layout.list_item_class_ongoing_parent_dash_board

            override fun getViewHolder(
                view: View,
                recyclerViewAdapter: RecyclerViewAdapter<StudentClass>
            ): ViewHolder<StudentClass> {

                return OngoingClassViewHolderStudentDashBoard(
                    ListItemClassOngoingParentDashBoardBinding.bind(view)
                )
            }
        }

        viewModel.studentClass.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                Result.Loading -> {
                }
                is Result.Success -> {
                    bindingParent.upcomingClassesRecyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
                        adapter = upComingAdapter
                    }

                    bindingParent.ongoingClassesRecyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
                        adapter = onGoingAdapter
                    }
                    val classes = result.data!!.StudentClasses
                    if (classes.isNullOrEmpty()) {
                        bindingParent.tvNoOngoing.makeVisible()
                        bindingParent.tvNoUpcoming.makeVisible()
                        bindingParent.ongoingClassesRecyclerView.makeInvisible()
                        bindingParent.upcomingClassesRecyclerView.makeInvisible()
                    } else {
                        bindingParent.tvNoOngoing.makeInvisible()
                        bindingParent.tvNoUpcoming.makeInvisible()
                        bindingParent.ongoingClassesRecyclerView.makeVisible()
                        bindingParent.upcomingClassesRecyclerView.makeVisible()
                        upComingAdapter.submitList(classes)
                        onGoingAdapter.submitList(classes)
                    }
                }
                is Result.Error -> {
                    bindingParent.root.snack(message = result.exception.localizedMessage)
                }
            }
        })

        //<--------------------------------- End - Upcoming and Ongoing class Setup ------------------------------------------>//

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
    }

    private fun setUpForTutor() {
        bindingTutor.walletBalance.text = 0.0.formatBalance()

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


        bindingTutor.modifyBtn.setOnClickListener {
            dataPicker()
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

                profile.students_count?.let {
                    bindingTutor.totalStudent.text = String.format("%s", profile.students_count)
                } ?: kotlin.run {
                    bindingTutor.totalStudent.text = String.format("%s", 0)
                }

                profile.profile_visits?.let {
                    bindingTutor.totalProfileVisits.text =
                        String.format("%s", profile.profile_visits)
                } ?: kotlin.run {
                    bindingTutor.totalProfileVisits.text = String.format("%s", 0)
                }
            }


        })

        viewModel.wallet.observe(viewLifecycleOwner, Observer {
            it?.let {
                bindingTutor.walletBalance.text = it.availableBalance.formatBalance()
            }
        })
    }

    private fun dataPicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        activity?.let {
            DatePickerDialog(
                it,
                DatePickerDialog.OnDateSetListener { _, _, _, _ ->
                    updateDateInView()
                }, year, month, day
            )
        }?.show()
    }

    private fun updateDateInView() {
        val myFormat = "MM/DD/YYYY" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        textViewDate?.text = sdf.format(c.time)

    }

    fun OnUserclicked(datamodel: TutorClassesDataModel, position: Int) {
        //OPEN INTENT  OR FRAGMENT TO EDIT THE TUTORS CLASSES
    }
}