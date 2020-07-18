package com.mobileforce.hometeach.models

import androidx.recyclerview.widget.DiffUtil

data class TutorDashboardModel(
    var id: String,
    var totalStudent: Int,
    var totalBalance: Int,
    var totalProfileVisits: Int,
    var totalReviews: Int

)

val tutorDashboardModelDiffUtil = object : DiffUtil.ItemCallback<TutorDashboardModel>() {
    override fun areItemsTheSame(
        oldItem: TutorDashboardModel,
        newItem: TutorDashboardModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TutorDashboardModel,
        newItem: TutorDashboardModel
    ): Boolean {
        return oldItem == newItem
    }
}