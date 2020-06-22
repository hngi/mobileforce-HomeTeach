package com.mobileforce.hometeach.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.ListItemClassTutorBinding
import com.mobileforce.hometeach.models.OngoingClassModelTutor

class OnGoingClassTutorAdapter : ListAdapter<OngoingClassModelTutor, OngoingTutorViewHolder>(DiffClass) {

    companion object DiffClass : DiffUtil.ItemCallback<OngoingClassModelTutor>() {
        override fun areItemsTheSame(
            oldItem: OngoingClassModelTutor,
            newItem: OngoingClassModelTutor
        ): Boolean {
            return oldItem.subject == newItem.subject
        }

        override fun areContentsTheSame(
            oldItem: OngoingClassModelTutor,
            newItem: OngoingClassModelTutor
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OngoingTutorViewHolder {
        return OngoingTutorViewHolder(ListItemClassTutorBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: OngoingTutorViewHolder, position: Int) {
       val ongoingClassTutor = getItem(position)
        holder.bind(ongoingClassTutor)
    }


}

class OngoingTutorViewHolder(val binding: ListItemClassTutorBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tutorOngoing: OngoingClassModelTutor) {
        binding.tutorModel = tutorOngoing
        binding.executePendingBindings()
    }

}