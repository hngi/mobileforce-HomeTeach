package com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.ListItemClassOngoingParentBinding
import com.mobileforce.hometeach.models.OngoingClassModel

/**
 * Created by Mayokun Adeniyi on 28/06/2020.
 */

class ParentOngoingClassesAdapter: ListAdapter<OngoingClassModel, ParentOngoingClassesAdapter.ViewHolder>(
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

    class ViewHolder(private val binding: ListItemClassOngoingParentBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(classModel: OngoingClassModel){
            binding.classModel = classModel
            binding.executePendingBindings()
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

    /**
     *  A utility class [DiffUtil] that helps to calculate updates for a [RecyclerView] Adapter.
     */
    class OngoingClassesDiffCallBack: DiffUtil.ItemCallback<OngoingClassModel>(){
        override fun areItemsTheSame(oldItem: OngoingClassModel, newItem: OngoingClassModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OngoingClassModel,
            newItem: OngoingClassModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}