package com.mobileforce.hometeach.models

import androidx.recyclerview.widget.DiffUtil

data class UpComingClassModel(
    val id: String,
    val subject: String,
    val startDate: String,
    val time: String,
    val tutorName: String,
    val tutorImage: String,
    val tutorSubject: String
)

val upComingDiffUtil = object : DiffUtil.ItemCallback<UpComingClassModel>() {
    override fun areItemsTheSame(
        oldItem: UpComingClassModel,
        newItem: UpComingClassModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UpComingClassModel,
        newItem: UpComingClassModel
    ): Boolean {
        return oldItem == newItem
    }

}