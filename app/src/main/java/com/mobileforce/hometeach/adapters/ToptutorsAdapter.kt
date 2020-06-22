package com.mobileforce.hometeach.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hometeach.R
import com.mobileforce.hometeach.models.ToptutorsDataModel
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.top_tutors_layout.view.*

class ToptutorsAdapter(
    private var itemlist: List<ToptutorsDataModel>,
    private val listener: Onclick
) : RecyclerView.Adapter<ToptutorsAdapter.RecyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val items = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.top_tutors_layout, parent
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


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val teachers_image = itemView.teachers_image
        val teachers_name = itemView.teachers_name
        val teachers_price = itemView.teachers_price
        val rating = itemView.teachers_rating
        val teachers_description = itemView.teachers_description

        fun initialise(datamodel: ToptutorsDataModel, listener: Onclick) {

            teachers_price.text = datamodel.teachers_price
           rating.rating = datamodel.teachers_rating.toFloat()
            teachers_name.text = datamodel.teachers_name
            teachers_description.text = datamodel.teahers_description
            Picasso.get().load(datamodel.teachers_image).into(teachers_image)


            itemView.setOnClickListener {
                listener.onUserClick(datamodel, adapterPosition)
            }

        }

    }
}

interface Onclick {
    fun onUserClick(datamodel: ToptutorsDataModel, position: Int)
}
