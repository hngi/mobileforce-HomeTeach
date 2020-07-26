package com.mobileforce.hometeach.ui.classes.tutor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.adapters.OnrequestClick
import com.mobileforce.hometeach.adapters.TutorRequestAdapter
import com.mobileforce.hometeach.databinding.FragmentTutorRequestBinding
import com.mobileforce.hometeach.models.Request
import com.mobileforce.hometeach.utils.makeVisible
import org.koin.android.ext.android.get

class TutorRequestFragment : Fragment(), OnrequestClick {
    private lateinit var binding: FragmentTutorRequestBinding
    private lateinit var requestList: MutableList<Request>
    lateinit var tutorId: String
    private val viewModel: TutorRequestViewModel = get<TutorRequestViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorRequestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?

        }
        requestList = mutableListOf()
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getTutorRequest()
        viewModel.tutorRequest.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.INVISIBLE
           if (it.requests.isNullOrEmpty()) {
               binding.tvNoRequest.makeVisible()
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
            "classes_request_id" to datamodel.classes_request_id,
            "id" to tutorId,
            "student_name" to datamodel.student_name,
            "grade" to datamodel.grade
        )
    }
}