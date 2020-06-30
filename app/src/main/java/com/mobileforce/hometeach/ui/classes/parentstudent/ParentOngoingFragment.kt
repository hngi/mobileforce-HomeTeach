package com.mobileforce.hometeach.ui.classes.parentstudent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileforce.hometeach.databinding.FragmentParentOngoingBinding
import com.mobileforce.hometeach.models.OngoingClassModel
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.ParentOngoingClassesAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [ParentOngoingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ParentOngoingFragment : Fragment() {
    private lateinit var binding: FragmentParentOngoingBinding
    private lateinit var parentOngoingClassesAdapter: ParentOngoingClassesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParentOngoingBinding.inflate(layoutInflater)
        parentOngoingClassesAdapter =
            ParentOngoingClassesAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerview
        val ongoingClassesList: MutableList<OngoingClassModel> = mutableListOf()
        ongoingClassesList.add(OngoingClassModel(1,"Physics","Tue, 10 July","16:00 - 18:00","Rahman Django","profile_image",40,"Physics Tutor"))
        ongoingClassesList.add(OngoingClassModel(2,"Biology","Tue, 10 July","16:00 - 18:00","Alice Snow","profile_image",100,"Biology Tutor"))
        ongoingClassesList.add(OngoingClassModel(3,"Mathematics","Tue, 10 July","16:00 - 18:00","Alice Snow","https://via.placeholder.com/300/09f/fff.png",100,"Mathematics Tutor"))
        ongoingClassesList.add(OngoingClassModel(1,"Chemistry","Tue, 10 July","16:00 - 18:00","Rahman Django","profile_image",40,"Physics Tutor"))
        ongoingClassesList.add(OngoingClassModel(2,"Geography","Tue, 10 July","16:00 - 18:00","Alice Snow","profile_image",100,"Biology Tutor"))
        ongoingClassesList.add(OngoingClassModel(3,"Computer Science","Tue, 10 July","16:00 - 18:00","Alice Snow","https://via.placeholder.com/300/09f/fff.png",100,"Mathematics Tutor"))
        ongoingClassesList.add(OngoingClassModel(1,"Fishery","Tue, 10 July","16:00 - 18:00","Rahman Django","profile_image",40,"Physics Tutor"))
        ongoingClassesList.add(OngoingClassModel(2,"Fine Arts","Tue, 10 July","16:00 - 18:00","Alice Snow","profile_image",100,"Biology Tutor"))
        ongoingClassesList.add(OngoingClassModel(3,"Drama","Tue, 10 July","16:00 - 18:00","Alice Snow","https://via.placeholder.com/300/09f/fff.png",100,"Mathematics Tutor"))
        parentOngoingClassesAdapter.submitList(ongoingClassesList)
        recyclerView.adapter = parentOngoingClassesAdapter
    }

}