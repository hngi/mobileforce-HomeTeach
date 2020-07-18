package com.mobileforce.hometeach.ui.tutorlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.ListItemAllTutorsBinding
import com.mobileforce.hometeach.models.TutorModel

class TutorListRecyclerAdapter(val clickListener: TutorListItemListener) : ListAdapter<TutorModel, TutorListViewHolder>(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorListViewHolder {
        return TutorListViewHolder(
            ListItemAllTutorsBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(listViewHolder: TutorListViewHolder, position: Int) {
        val tutor = getItem(position)
        listViewHolder.bind(tutor,clickListener)
    }


}

class TutorListViewHolder(private val binding: ListItemAllTutorsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tutorAll: TutorModel, clickListener: TutorListItemListener) {
        binding.tutor = tutorAll
        binding.executePendingBindings()
        binding.clickListener = clickListener
    }

}

class TutorListItemListener(val clickListener: (tutorId: String?) -> Unit){
     fun onClick(tutor: TutorModel) {
         clickListener(tutor.id)
     }
}