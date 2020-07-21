package com.mobileforce.hometeach.adapters

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mobileforce.hometeach.utils.inflate

/**
 * Authored by enyason
 *
 * this is a generic adapter  for populating all List views
 */

abstract class RecyclerViewAdapter<T>(diffUtil: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, ViewHolder<T>>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return getViewHolder(parent.inflate(viewType), this)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = getLayoutRes(getItem(position))


    @LayoutRes
    abstract fun getLayoutRes(model: T): Int

    abstract fun getViewHolder(
        view: View,
        recyclerViewAdapter: RecyclerViewAdapter<T>
    ): ViewHolder<T>

}