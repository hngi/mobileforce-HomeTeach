package com.mobileforce.hometeach.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.ListItemAllTutorsBinding
import com.mobileforce.hometeach.models.TutorAllModel

class AllTutorRecyclerAdapter : ListAdapter<TutorAllModel, AllTutorViewHolder>(DiffClass) {

    companion object DiffClass : DiffUtil.ItemCallback<TutorAllModel>() {
        override fun areItemsTheSame(
            oldItem: TutorAllModel,
            newItem: TutorAllModel
        ): Boolean {
            return oldItem.tutorName == newItem.tutorName
        }

        override fun areContentsTheSame(
            oldItem: TutorAllModel,
            newItem: TutorAllModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTutorViewHolder {
        return AllTutorViewHolder(ListItemAllTutorsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AllTutorViewHolder, position: Int) {
        val AllTutor = getItem(position)
        holder.bind(AllTutor)
    }


}

class AllTutorViewHolder(private val binding: ListItemAllTutorsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tutorAll: TutorAllModel) {
        binding.tutor = tutorAll
        binding.executePendingBindings()
    }

}