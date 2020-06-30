package com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.ListItemClassOngoingTutorBinding
import com.mobileforce.hometeach.models.OngoingClassModelTutor

/**
 * Created by Mayokun Adeniyi on 28/06/2020.
 */

class TutorOngoingClassesAdapter: ListAdapter<OngoingClassModelTutor, TutorOngoingClassesAdapter.ViewHolder>(
    OngoingClassesDiffCallBack()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ongoingClassModel = getItem(position)
        holder.bind(ongoingClassModel)
    }

    class ViewHolder(private val binding: ListItemClassOngoingTutorBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(classModel: OngoingClassModelTutor){
            binding.classModel = classModel
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemClassOngoingTutorBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

    /**
     *  A utility class [DiffUtil] that helps to calculate updates for a [RecyclerView] Adapter.
     */
    class OngoingClassesDiffCallBack: DiffUtil.ItemCallback<OngoingClassModelTutor>(){
        override fun areItemsTheSame(oldItem: OngoingClassModelTutor, newItem: OngoingClassModelTutor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OngoingClassModelTutor,
            newItem: OngoingClassModelTutor
        ): Boolean {
            return oldItem == newItem
        }
    }
}