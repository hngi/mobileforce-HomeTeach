package com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.data.sources.remote.wrappers.Request
import com.mobileforce.hometeach.data.sources.remote.wrappers.StudentClass
import com.mobileforce.hometeach.data.sources.remote.wrappers.studentClassDiffUtil
import com.mobileforce.hometeach.data.sources.remote.wrappers.userRequestDiffUtil
import com.mobileforce.hometeach.databinding.ListItemClassUpcomingParentBinding
import com.mobileforce.hometeach.utils.loadImage
import java.net.URL

/**
 * Created by Mayokun Adeniyi on 28/06/2020.
 * Modified by MayorJay
 */

class ParentUpcomingClassesAdapter: ListAdapter<StudentClass, ParentUpcomingClassesAdapter.ViewHolder>(studentClassDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val studentClass = getItem(position)
        holder.bind(studentClass)
    }

    class ViewHolder(private val binding: ListItemClassUpcomingParentBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(studentClass: StudentClass) {
//            val currentDateTime = System.currentTimeMillis()
//            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH-mm", Locale.US)
//            val startRequestDateTime = dateFormat.parse(request.day + " " + request.from_hour + ":" + request.from_minute)!!.time
//            val endRequestDateTime = dateFormat.parse(request.day + " " + request.to_hour + ":" + request.to_minute)!!.time
            with(studentClass) {
                binding.subjectName.text = subject
                binding.dateTime.text = "$day $from_hour:$from_minute-$to_hour:$to_minute"
                binding.tutorName.text = tutor_name
                binding.tutorSubject.text = "$subject Tutor"
                binding.tutorImage.loadImage(URL(tutor_pic))
//                if (startRequestDateTime < currentDateTime || currentDateTime < endRequestDateTime) {
//                }
            }
        }
        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemClassUpcomingParentBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
}