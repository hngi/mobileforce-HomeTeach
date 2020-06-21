package com.mobileforce.hometeach.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R

import com.mobileforce.hometeach.adapters.OngoingRecyclerAdapter
import com.mobileforce.hometeach.models.OngoingClassModel
import kotlinx.android.synthetic.main.fragment_ongoing.*
import kotlinx.android.synthetic.main.list_item_class.*

/**
 * A simple [Fragment] subclass.
 */
class OngoingFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var ongoing_classes_list:MutableList<OngoingClassModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ongoing, container, false)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here
        ongoing_classes_list.add(OngoingClassModel("Physics - Insulators - Module 1","Tue, 10 July","16:00 - 18:00","Rahman Django","profile_image",40,"Physics Tutor"))
        ongoing_classes_list.add(OngoingClassModel("Biology - Module 2","Tue, 10 July","16:00 - 18:00","Alice Snow","profile_image",100,"Biology Tutor"))
        ongoing_classes_list.add(OngoingClassModel("Mathematics - Module 1","Tue, 10 July","16:00 - 18:00","Alice Snow","https://via.placeholder.com/300/09f/fff.png",100,"Mathematics Tutor"))
//        Log.d("count",ongoing_classes_list.size.toString())
        classes_recycler_view.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false) as RecyclerView.LayoutManager?
            adapter = OngoingRecyclerAdapter(ongoing_classes_list)

        }
        classes_recycler_view.setHasFixedSize(true)


    }
    companion object {
        fun newInstance(): OngoingFragment =
            OngoingFragment()
    }
}
