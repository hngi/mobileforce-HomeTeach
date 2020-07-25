package com.mobileforce.hometeach.ui.classes.tutor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.OnrequestClick
import com.mobileforce.hometeach.adapters.TutorRequestAdapter
import com.mobileforce.hometeach.databinding.FragmentTutorRequestBinding
import com.mobileforce.hometeach.models.Request
import com.mobileforce.hometeach.models.RequestClassModel
import com.mobileforce.hometeach.models.TutorRequestDataModel
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.TutorRequestClassesAdapter
import com.mobileforce.hometeach.ui.profile.EditTutorViewModel
import org.koin.android.ext.android.get



class TutorRequestFragment : Fragment(), OnrequestClick {
    private lateinit var binding: FragmentTutorRequestBinding
    private lateinit var requestList: MutableList<Request>
    lateinit var tutorId: String
//    lateinit var navController: NavController
//    lateinit var adapter:TutorRequestAdapter

    private val viewModel: TutorRequestViewModel = get<TutorRequestViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorRequestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        navController =  Navigation.findNavController(view)
        val recyclerView = binding.recyclerView
        Log.d("coffee", "INSIDE TUTOR REQUEST")

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?

        }
        requestList = mutableListOf()
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getTutorRequest()
        viewModel.tutorRequest.observe(viewLifecycleOwner, Observer {
            Log.d("deve",it.requests.toString())
            binding.progressBar.visibility = View.INVISIBLE
           if (it.requests.isNullOrEmpty()) {
                Log.d("coffee",it.toString())
               Toast.makeText(activity, "YOU HAVE NO PENDING REQUEST", Toast.LENGTH_SHORT).show()
            }
           requestList = it.requests as MutableList<Request>
            val adapter = TutorRequestAdapter(requestList, this)
            recyclerView.adapter = adapter
        })


        viewModel.tutorId.observe(viewLifecycleOwner, Observer {
            tutorId = it

        })

    }

    override fun onUserClick(datamodel: Request, position: Int) {
        val bundle = bundleOf(
            "student_pic" to datamodel.student_pic,
            "request_id" to datamodel.classes_request_id,
            "id" to tutorId,
            "student_name" to datamodel.student_name,
            "student_garde" to datamodel.grade
        )
//        navController.navigate(R.id.studentDetails,bundle)
//        val intent = Intent(activity,SudentDetailsActivity::class.java)
    }


}