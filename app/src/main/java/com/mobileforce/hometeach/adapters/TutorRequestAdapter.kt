package com.mobileforce.hometeach.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.models.Request
import kotlinx.android.synthetic.main.list_item_class_requests_tutor.view.subject_name
import kotlinx.android.synthetic.main.tutor_request_layout.view.*

class TutorRequestAdapter(
    private val itemsList: List<Request>,
    val listener:
    OnrequestClick
) : RecyclerView.Adapter<TutorRequestAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val items = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.tutor_request_layout, parent
                , false
            )
        return RecyclerViewHolder(items)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.initialise(itemsList[position], listener)
    }


    class RecyclerViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val subject: TextView = itemview.subject_name
        val studentName: TextView = itemview.student_name
        val status: TextView = itemview.status
        val date: TextView = itemview.date
        val time: TextView = itemview.time
        val grade: TextView = itemview.grade


        fun initialise(datamodel: Request, listener: OnrequestClick) {
            subject.text = datamodel.subject
            studentName.text = datamodel.student_name
            date.text = datamodel.date_requested
            if (datamodel.accepted) {
                status.text = "Accepted"
            } else {
                status.text = "Awaiting Approval"
            }
            grade.text = datamodel.grade
            //time.text = "${datamodel.from_hour}:${datamodel.from_minute}-${datamodel.to_hour}:${datamodel.to_minute}"

            itemView.setOnClickListener {
                listener.onUserClick(datamodel, adapterPosition)
            }

        }

    }
}

interface OnrequestClick {
    fun onUserClick(datamodel: Request, position: Int)
}