package com.mobileforce.hometeach.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.models.TutorClassesDataModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ongoing_classes_layout.view.*
import kotlinx.android.synthetic.main.ongoing_classes_layout.view.progress
import kotlinx.android.synthetic.main.tutors_classes.view.*
import kotlinx.android.synthetic.main.upcoming_classes_layout.view.*

class TutorClassesAdapter(
    private var itemlist: List<TutorClassesDataModel>,
    val listener: OnItemtouch
) : RecyclerView.Adapter<TutorClassesAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val items = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.tutors_classes, parent
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

        val percent_complete: TextView = itemview.completion
        val progress: ProgressBar = itemview.progress
        val teachers_name: TextView = itemview.tutor_name
        val teachers_subject: TextView = itemview.tutor_subject
        val teachers_image = itemview.tutor_image


        fun initialise(datamodel: TutorClassesDataModel, listener: OnItemtouch) {

            teachers_subject.text = datamodel.tutors_subject
            percent_complete.text = datamodel.completion.toString()
            progress.progress = datamodel.progress
            teachers_name.text = datamodel.tutors_name
            teachers_subject.text = datamodel.tutors_subject
            Picasso.get().load(datamodel.tutorsImage).into(teachers_image)


            itemView.setOnClickListener {
                listener.OnUserclicked(datamodel, adapterPosition)
            }

        }

    }


}


interface OnItemtouch {
    fun OnUserclicked(datamodel: TutorClassesDataModel, position: Int)
}