package com.mobileforce.hometeach.data.sources.remote.wrappers

import androidx.recyclerview.widget.DiffUtil

data class UserClassResponse(
    val student_id: String,
    val requests: List<Request>
)

data class Request(
    val accepted: Boolean,
    val declined: Boolean,
    val request_id: String,
    val date_requested: String,
    val classes_request_id: String,
    val subject: String,
    val day: String,
    val from_minute: String,
    val to_minute: String,
    val from_hour: String,
    val to_hour: String,
    val grade: String,
    val tutor_name: String,
    val tutor_id: String,
    val tutor_pic: String
)

val userRequestDiffUtil = object : DiffUtil.ItemCallback<Request>() {
    override fun areItemsTheSame(
        oldItem: Request,
        newItem: Request
    ): Boolean {
        return oldItem.request_id == newItem.request_id
    }

    override fun areContentsTheSame(
        oldItem: Request,
        newItem: Request
    ): Boolean {
        return oldItem == newItem
    }
}