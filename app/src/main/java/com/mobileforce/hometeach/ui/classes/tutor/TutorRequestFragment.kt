package com.mobileforce.hometeach.ui.classes.tutor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileforce.hometeach.databinding.FragmentTutorRequestBinding
import com.mobileforce.hometeach.models.RequestClassModel
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.TutorRequestClassesAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [TutorRequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TutorRequestFragment : Fragment() {
    private lateinit var binding: FragmentTutorRequestBinding
    private lateinit var requestClassesAdapter: TutorRequestClassesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorRequestBinding.inflate(layoutInflater)
        requestClassesAdapter =
            TutorRequestClassesAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        val requestClassList: MutableList<RequestClassModel> = mutableListOf()
        requestClassList.add(RequestClassModel(1,"Mathematics",2000.0,"Jake Wharton","Wed, 12 March","13:00-15:00",3,"Approved",false))
        requestClassList.add(RequestClassModel(2,"English",1000.0,"Barry Allen","Tue, 14 March","08:00-10:00",1,"Awaiting Approval",false))
        requestClassList.add(RequestClassModel(3,"Music",500.0,"Mark Essien","Thur, 13 June","16:00-19:00",2,"",true))
        requestClassList.add(RequestClassModel(4,"Geography",5000.0,"Raul Garcia","Tue, 14 April","11:00-13:00",3,"Approved",false))
        requestClassList.add(RequestClassModel(5,"Fine Arts",2500.0,"James Isco","Wed, 30 May","16:00-19:00",1,"",true))
        requestClassList.add(RequestClassModel(6,"Yoruba",2060.0,"Daniel James","Mon, 21 June","16:00-19:00",1,"Awaiting Approval",false))
        requestClassList.add(RequestClassModel(7,"Physics",100.0,"Aurora Times","Fri, 12 July","16:00-19:00",3,"",true))
        requestClassList.add(RequestClassModel(8,"Chemistry",200.0,"Buhari King","Tue, 02 August","16:00-19:00",2,"Approved",false))
        requestClassesAdapter.submitList(requestClassList)
        recyclerView.adapter = requestClassesAdapter
    }
}