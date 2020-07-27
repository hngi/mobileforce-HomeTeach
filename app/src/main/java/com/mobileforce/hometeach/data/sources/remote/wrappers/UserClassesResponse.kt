package com.mobileforce.hometeach.data.sources.remote.wrappers

import androidx.recyclerview.widget.DiffUtil

data class UserClassesResponse(
    val student_id: String,
    val StudentClasses: List<StudentClass>
)

data class StudentClass(
    val request_id: String,
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

val studentClassDiffUtil = object : DiffUtil.ItemCallback<StudentClass>() {
    override fun areItemsTheSame(
        oldItem: StudentClass,
        newItem: StudentClass
    ): Boolean {
        return oldItem.request_id == newItem.request_id
    }

    override fun areContentsTheSame(
        oldItem: StudentClass,
        newItem: StudentClass
    ): Boolean {
        return oldItem == newItem
    }
}