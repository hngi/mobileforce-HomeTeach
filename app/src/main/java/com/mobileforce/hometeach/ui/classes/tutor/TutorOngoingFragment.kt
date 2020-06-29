package com.mobileforce.hometeach.ui.classes.tutor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileforce.hometeach.databinding.FragmentTutorOngoingBinding
import com.mobileforce.hometeach.models.OngoingClassModelTutor
import com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters.TutorOngoingClassesAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [TutorOngoingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TutorOngoingFragment : Fragment() {

    private lateinit var binding: FragmentTutorOngoingBinding
    private lateinit var tutorOngoingClassesAdapter: TutorOngoingClassesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorOngoingBinding.inflate(layoutInflater)
        tutorOngoingClassesAdapter =
            TutorOngoingClassesAdapter()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        val ongoingClassesList: MutableList<OngoingClassModelTutor> = mutableListOf()
        ongoingClassesList.add(OngoingClassModelTutor(1,"Physics","Tue, 10 July","16:00 - 18:00","Rahman Django","profile_image",40,1))
        ongoingClassesList.add(OngoingClassModelTutor(2,"Biology","Tue, 10 July","16:00 - 18:00","Alice Snow","profile_image",100,4))
        ongoingClassesList.add(OngoingClassModelTutor(3,"Mathematics","Tue, 10 July","16:00 - 18:00","Alice Snow","https://via.placeholder.com/300/09f/fff.png",100,3))
        ongoingClassesList.add(OngoingClassModelTutor(1,"Chemistry","Tue, 10 July","16:00 - 18:00","Rahman Django","profile_image",40,5))
        ongoingClassesList.add(OngoingClassModelTutor(2,"Geography","Tue, 10 July","16:00 - 18:00","Alice Snow","profile_image",100,1))
        ongoingClassesList.add(OngoingClassModelTutor(3,"Computer Science","Tue, 10 July","16:00 - 18:00","Alice Snow","https://via.placeholder.com/300/09f/fff.png",100,6))
        ongoingClassesList.add(OngoingClassModelTutor(1,"Fishery","Tue, 10 July","16:00 - 18:00","Rahman Django","profile_image",40,6))
        ongoingClassesList.add(OngoingClassModelTutor(2,"Fine Arts","Tue, 10 July","16:00 - 18:00","Alice Snow","profile_image",100,2))
        ongoingClassesList.add(OngoingClassModelTutor(3,"Drama","Tue, 10 July","16:00 - 18:00","Alice Snow","https://via.placeholder.com/300/09f/fff.png",100,6))
        tutorOngoingClassesAdapter.submitList(ongoingClassesList)
        recyclerView.adapter = tutorOngoingClassesAdapter
    }

}