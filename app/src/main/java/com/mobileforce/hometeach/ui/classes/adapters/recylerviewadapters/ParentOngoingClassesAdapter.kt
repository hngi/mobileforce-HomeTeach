package com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.data.sources.remote.wrappers.Request
import com.mobileforce.hometeach.data.sources.remote.wrappers.userRequestDiffUtil
import com.mobileforce.hometeach.databinding.ListItemClassOngoingParentBinding
import com.mobileforce.hometeach.models.OngoingClassModel
import com.mobileforce.hometeach.utils.loadImage
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Mayokun Adeniyi on 28/06/2020.
 * Modified by MayorJay
 */

class ParentOngoingClassesAdapter: ListAdapter<Request, ParentOngoingClassesAdapter.ViewHolder>(userRequestDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val request = getItem(position)
        holder.bind(request)
    }

    class ViewHolder(private val binding: ListItemClassOngoingParentBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(request: Request) {
//            val currentDateTime = System.currentTimeMillis()
//            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH-mm", Locale.US)
//            val startRequestDateTime = dateFormat.parse(request.day + " " + request.from_hour + ":" + request.from_minute)!!.time
//            val endRequestDateTime = dateFormat.parse(request.day + " " + request.to_hour + ":" + request.to_minute)!!.time
            with(request) {
                binding.subjectName.text = subject
                binding.classProgress.progress = 0 //(currentDateTime/endRequestDateTime).toInt() * 100
                binding.classDate.text = day
                binding.classTime.text = "$from_hour:$from_minute-$to_hour:$to_minute"
                binding.tutorImage.loadImage(URL(tutor_pic))
                binding.tutorName.text = tutor_name
                binding.tutorSubject.text = "$subject Tutor"
//                if (startRequestDateTime < currentDateTime || currentDateTime < endRequestDateTime) {
//                }
            }
        }
        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemClassOngoingParentBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
}