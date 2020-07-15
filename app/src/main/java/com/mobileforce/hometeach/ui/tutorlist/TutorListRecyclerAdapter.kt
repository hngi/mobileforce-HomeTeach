package com.mobileforce.hometeach.ui.tutorlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.ListItemAllTutorsBinding
import com.mobileforce.hometeach.models.TutorAllModel

class TutorListRecyclerAdapter : ListAdapter<TutorAllModel, TutorListViewHolder>(
    DiffClass
) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorListViewHolder {
        return TutorListViewHolder(
            ListItemAllTutorsBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(listViewHolder: TutorListViewHolder, position: Int) {
        val tutor = getItem(position)
        listViewHolder.bind(tutor)
    }


}

class TutorListViewHolder(private val binding: ListItemAllTutorsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tutorAll: TutorAllModel) {
        binding.tutor = tutorAll
        binding.executePendingBindings()
        binding.outlineButton.setOnClickListener {
            println(tutorAll.id)
        }
    }

}