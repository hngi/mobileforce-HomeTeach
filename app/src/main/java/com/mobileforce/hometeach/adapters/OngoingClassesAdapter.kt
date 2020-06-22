package com.mobileforce.hometeach.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.models.OngoingClassesDataModel
import com.mobileforce.hometeach.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ongoing_classes_layout.view.*

import kotlinx.android.synthetic.main.upcoming_classes_layout.view.teachers_image
import kotlinx.android.synthetic.main.upcoming_classes_layout.view.teachers_name



class OngoingClassesAdapter(
    private var itemlist: List<OngoingClassesDataModel>,
    val listener: OnUserclick
) :
    RecyclerView.Adapter<OngoingClassesAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val items = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.ongoing_classes_layout, parent
                , false
            )

        return RecyclerViewHolder(
            items
        )
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = itemlist[position]
        holder.initialise(itemlist.get(position), listener)
    }


    class RecyclerViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val subject: TextView = itemview.subject
        val percent_complete: TextView = itemview.percent_complete
        val progress: ProgressBar = itemview.progress
        val teachers_name: TextView = itemview.teachers_name
        val teachers_subject: TextView = itemview.teachers_subject
        val teachers_image = itemview.teachers_image


        fun initialise(datamodel: OngoingClassesDataModel, listener: OnUserclick) {

            subject.text = datamodel.subject
            percent_complete.text = datamodel.completion
            progress.progress = datamodel.progress
            teachers_name.text = datamodel.teachers_name
            teachers_subject.text = datamodel.teachers_subject
            Picasso.get().load(datamodel.teachers_image).into(teachers_image)


            itemView.setOnClickListener {
                listener.onUserClick(datamodel, adapterPosition)
            }

        }

    }


}

interface OnUserclick {
    fun onUserClick(datamodel: OngoingClassesDataModel, position: Int)
}