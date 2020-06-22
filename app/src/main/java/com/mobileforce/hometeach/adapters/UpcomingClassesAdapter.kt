package com.mobileforce.hometeach.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hometeach.R
import com.mobileforce.hometeach.models.UpcomingClassesDataModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.upcoming_classes_layout.view.*


class UpcomingClassesAdapter(private var itemlist: List<UpcomingClassesDataModel>, val listener: OnUserClick) :
    RecyclerView.Adapter<UpcomingClassesAdapter.RecyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val items = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.upcoming_classes_layout, parent
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

        val teachers_price:TextView = itemview.teachers_price //itemview.findViewById(R.id.subject) //itemview.subject
        val teachers_name:TextView = itemview.teachers_name//itemview.findViewById(R.id.teachers_name)//itemview.teachers_name
        val teachers_description:TextView =  itemview.teachers_description//itemview.findViewById(R.id.teachers_subject) //itemview.teachers_subject
        val teachers_image = itemview.teachers_image //itemview.findViewById<Cycl>(R.id.teachers_image)
        fun initialise(datamodel: UpcomingClassesDataModel, listener: OnUserClick) {

            teachers_price.text = datamodel.teachers_price
            teachers_name.text = datamodel.teachers_name
            teachers_description.text = datamodel.description

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