package com.example.hometeach

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.upcoming_classes_layout.view.*

class UpcomingClassesAdapter(private var itemlist: List<UpcomingClassesDataModel>,val listener:OnUserClick) :
    RecyclerView.Adapter<UpcomingClassesAdapter.RecyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val items = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.upcoming_classes_layout, parent
                , false
            )

        return RecyclerViewHolder(items)
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = itemlist[position]
        holder.initialise(itemlist.get(position), listener)
    }

    class RecyclerViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val subject:TextView = itemview.subject
        val date:TextView = itemview.date
        val teachers_name:TextView = itemview.teachers_name
        val teachers_subject:TextView = itemview.teachers_subject
        val teachers_image = itemview.teachers_image
        fun initialise(datamodel: UpcomingClassesDataModel, listener: OnUserClick) {

            subject.text = datamodel.subject
            date.text = datamodel.date
            teachers_name.text = datamodel.teachers_name
            teachers_subject.text = datamodel.teachers_subject
            Picasso.get().load(datamodel.teachers_image).into(teachers_image)


            itemView.setOnClickListener {
                listener.onUserClick(datamodel, adapterPosition)
            }

        }

    }

}


interface OnUserClick {
    fun onUserClick(datamodel: UpcomingClassesDataModel, position: Int)
}