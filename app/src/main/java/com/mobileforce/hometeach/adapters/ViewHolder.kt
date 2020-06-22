package com.mobileforce.hometeach.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Authored by enyason
 */

abstract class ViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(element: T)
}

