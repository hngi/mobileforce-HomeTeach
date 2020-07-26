package com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.data.sources.remote.wrappers.Request
import com.mobileforce.hometeach.data.sources.remote.wrappers.userRequestDiffUtil
import com.mobileforce.hometeach.databinding.ListItemClassRequestsParentBinding
import com.mobileforce.hometeach.models.RequestClassModel

/**
 * Created by Mayokun Adeniyi on 29/06/2020.
 * Modified by MayorJay
 */

class ParentRequestClassesAdapter :
    ListAdapter<Request, ParentRequestClassesAdapter.ViewHolder>(userRequestDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val request = getItem(position)
        holder.bind(request)
    }

    class ViewHolder(private val binding: ListItemClassRequestsParentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(request: Request) {
            with(request) {
                binding.subjectName.text = subject
                binding.classDate.text = day
                binding.classTime.text = "$from_hour:$from_minute-$to_hour:$to_minute"
                binding.tutorName.text = "Tutor: $tutor_name"
                binding.tvGrade.text = "Grade $grade"
                binding.tvStatus.text = if (accepted) "Status: Accepted" else "Status: Declined"
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ListItemClassRequestsParentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
}