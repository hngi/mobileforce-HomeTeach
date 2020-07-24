package com.mobileforce.hometeach.ui.classes.tutor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.adapters.OnrequestClick
import com.mobileforce.hometeach.adapters.TutorRequestAdapter
import com.mobileforce.hometeach.databinding.FragmentTutorRequestBinding
import com.mobileforce.hometeach.models.RequestClassModel
import com.mobileforce.hometeach.models.TutorRequestDataModel
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.TutorRequestClassesAdapter
import com.mobileforce.hometeach.ui.profile.EditTutorViewModel
import org.koin.android.ext.android.get


/**
 * A simple [Fragment] subclass.
 * Use the [TutorRequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TutorRequestFragment : Fragment(), OnrequestClick {
    private lateinit var binding: FragmentTutorRequestBinding
    private lateinit var requestClassesAdapter: TutorRequestAdapter
    private lateinit var requestList: MutableList<TutorRequestDataModel>

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
        viewModel.getTutorRequest()
        viewModel.tutorRequest.observe(viewLifecycleOwner, Observer {

           Log.d("dev",it.toString())

        })
//        requestList.add("")
//        val adapter = TutorRequestAdapter(requestList, this)
//        recyclerView.adapter = adapter
    }

    override fun onUserClick(datamodel: TutorRequestDataModel, position: Int) {

    }
}