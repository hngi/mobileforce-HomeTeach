package com.mobileforce.hometeach.ui.classes.tutor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.adapters.TutorRequestAdapter
import com.mobileforce.hometeach.models.Request
import com.mobileforce.hometeach.models.Schedule

class TutorUpcomingAdapter(private val itemsList: List<Schedule>):RecyclerView.Adapter<TutorUpcomingAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val items = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.tutor_request_layout, parent
                , false
            )
        return TutorUpcomingAdapter.RecyclerViewHolder(items)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        TODO("Not yet implemented")
    }



    class RecyclerViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview) {



    }

}