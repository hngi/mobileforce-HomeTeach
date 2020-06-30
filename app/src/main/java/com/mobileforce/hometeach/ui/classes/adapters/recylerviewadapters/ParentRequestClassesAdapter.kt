package com.mobileforce.hometeach.ui.classes.adapters.recylerviewadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.ListItemClassRequestsParentBinding
import com.mobileforce.hometeach.models.RequestClassModel

/**
 * Created by Mayokun Adeniyi on 29/06/2020.
 */

class ParentRequestClassesAdapter :
    ListAdapter<RequestClassModel, ParentRequestClassesAdapter.ViewHolder>(
        RequestClassesDiffCallBack()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val requestClassModel = getItem(position)
        holder.bind(requestClassModel)
    }

    class ViewHolder(private val binding: ListItemClassRequestsParentBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(classModel: RequestClassModel) {
            binding.requestClass = classModel
            binding.executePendingBindings()
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

    /**
     *  A utility class [DiffUtil] that helps to calculate updates for a [RecyclerView] Adapter.
     */
    class RequestClassesDiffCallBack : DiffUtil.ItemCallback<RequestClassModel>() {

        override fun areContentsTheSame(
            oldItem: RequestClassModel,
            newItem: RequestClassModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: RequestClassModel,
            newItem: RequestClassModel
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}