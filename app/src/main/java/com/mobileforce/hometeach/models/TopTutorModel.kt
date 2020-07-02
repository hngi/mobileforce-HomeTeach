package com.mobileforce.hometeach.models

import androidx.recyclerview.widget.DiffUtil

data class TopTutorModel(
    var id: String,
    var name: String,
    var rating: Double,
    var tutorImage: String,
    var tutorSubject: String,
    var tutorBio: String
)

val topTutorDiffUtil = object : DiffUtil.ItemCallback<TopTutorModel>() {
    override fun areItemsTheSame(
        oldItem: TopTutorModel,
        newItem: TopTutorModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TopTutorModel,
        newItem: TopTutorModel
    ): Boolean {
        return oldItem == newItem
    }

}