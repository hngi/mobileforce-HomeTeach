package com.example.hometeach.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hometeach.R

import com.example.hometeach.adapter.OngoingRecyclerAdapter
import com.example.hometeach.datamodel.OngoingClassModel
import kotlinx.android.synthetic.main.fragment_ongoing.*

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
        ongoing_classes_list = OngoingClassModel.getClassesFromFile("data.json", requireContext())
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
