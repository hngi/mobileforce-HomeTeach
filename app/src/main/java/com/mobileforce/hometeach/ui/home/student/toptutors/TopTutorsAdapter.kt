package com.mobileforce.hometeach.ui.home.student.toptutors


/**
 * Created by Mayokun Adeniyi on 21/07/2020.
 */

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.ListItemHomeTutorsRecyclerBinding
import com.mobileforce.hometeach.models.TutorModel

class TopTutorsAdapter(val clickListener: TopTutorsListItemListener) : ListAdapter<TutorModel, TopTutorsListViewHolder>(
    DiffClass
) {

    companion object DiffClass : DiffUtil.ItemCallback<TutorModel>() {
        override fun areItemsTheSame(
            oldItem: TutorModel,
            newItem: TutorModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TutorModel,
            newItem: TutorModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopTutorsListViewHolder {
        return TopTutorsListViewHolder(
            ListItemHomeTutorsRecyclerBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(listViewHolder: TopTutorsListViewHolder, position: Int) {
        val tutor = getItem(position)
        listViewHolder.bind(tutor,clickListener)
    }


}

class TopTutorsListViewHolder(private val binding: ListItemHomeTutorsRecyclerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tutorAll: TutorModel, clickListener: TopTutorsListItemListener) {
        binding.tutor = tutorAll
        binding.listener = clickListener
        binding.executePendingBindings()
    }

}

class TopTutorsListItemListener(val clickListener: (tutor: TutorModel?) -> Unit) {
    fun onClick(tutor: TutorModel) {
        clickListener(tutor)
    }
}