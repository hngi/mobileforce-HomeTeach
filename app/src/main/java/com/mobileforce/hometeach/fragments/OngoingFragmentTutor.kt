package com.mobileforce.hometeach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.OnGoingClassTutorAdapter
import com.mobileforce.hometeach.models.OngoingClassModelTutor
import kotlinx.android.synthetic.main.fragment_ongoing.*

class OngoingFragmentTutor :Fragment(){
    private lateinit var ongoingClassesList:MutableList<OngoingClassModelTutor>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ongoing, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ongoingClassesList.add(OngoingClassModelTutor("Physics - Insulators - Module 1","Tue, 10 July","16:00 - 18:00","Rahman Django","profile_image",40,4))
        ongoingClassesList.add(OngoingClassModelTutor("Biology - Module 2","Tue, 10 July","16:00 - 18:00","Alice Snow","profile_image",100,5))
        ongoingClassesList.add(OngoingClassModelTutor("Mathematics - Module 1","Tue, 10 July","16:00 - 18:00","Alice Snow","https://via.placeholder.com/300/09f/fff.png",100,7))
        val adapter =  OnGoingClassTutorAdapter()
        adapter.submitList(ongoingClassesList)
        classes_recycler_view.adapter = adapter

    }
    companion object {
        fun newInstance(): OngoingFragmentTutor =
            OngoingFragmentTutor()
    }
}

